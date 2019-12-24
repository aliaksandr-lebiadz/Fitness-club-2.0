package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public abstract class AbstractUserDao extends AbstractDao<User> implements UserDao {

    private static final String USER_TABLE = "fitness_user";
    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD_QUERY =
            "SELECT * FROM fitness_user WHERE email = ? AND password = ?";
    private static final String FIND_USERS_BY_TRAINER_ID_QUERY =
            "SELECT DISTINCT u.id, email, password, role, discount, first_name, second_name " +
                    "FROM fitness_user AS u " +
                    "JOIN client_order AS o ON u.id = o.client_id " +
                    "WHERE o.trainer_id = ?";
    private static final String GET_ALL_CLIENTS = "" +
            "SELECT * FROM fitness_user WHERE role = 'client'";

    public AbstractUserDao(JdbcTemplate jdbcTemplate, RowMapper<User> rowMapper){
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return executeForSingleResult(FIND_USER_BY_EMAIL_AND_PASSWORD_QUERY, email, password);
    }

    @Override
    public List<User> findUsersByTrainerId(long trainerId) {
        return executeQuery(FIND_USERS_BY_TRAINER_ID_QUERY, trainerId);
    }

    @Override
    public List<User> getAllClients() {
        return executeQuery(GET_ALL_CLIENTS);
    }

    @Override
    protected String getTableName() {
        return USER_TABLE;
    }

    @Override
    protected Object[] getFields(User user) {
        UserRole role = user.getRole();
        String roleValue = role.toString();
        return new Object[] {
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getSecondName(),
                roleValue.toLowerCase(),
                user.getDiscount()
        };
    }
}
