package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public abstract class AbstractUserDao extends AbstractDao<User> implements UserDao {

    private static final String USER_TABLE = "fitness_user";
    private static final String FIND_USERS_BY_TRAINER_ID_QUERY =
            "SELECT DISTINCT u.id, email, password, role, discount, first_name, second_name " +
                    "FROM fitness_user AS u " +
                    "JOIN client_order AS o ON u.id = o.client_id " +
                    "WHERE o.trainer_id = ?";
    private static final String GET_ALL_CLIENTS = "" +
            "SELECT * FROM fitness_user WHERE role = 'CLIENT'";
    private static final String FIND_USER_BY_EMAIL_QUERY =
            "SELECT * FROM fitness_user WHERE email = ?";
    private static final String GET_RANDOM_TRAINER_QUERY =
            "SELECT * FROM fitness_user WHERE role = 'TRAINER' ORDER BY RAND() LIMIT 1";

    public AbstractUserDao(JdbcTemplate jdbcTemplate, RowMapper<User> rowMapper){
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public List<User> findClientsOfTrainer(User trainer) {
        int trainerId = trainer.getId();
        return executeQuery(FIND_USERS_BY_TRAINER_ID_QUERY, trainerId);
    }

    @Override
    public List<User> getAllClients() {
        return executeQuery(GET_ALL_CLIENTS);
    }

    @Override
    public Optional<User> findUserByEmail(String email){
        return executeForSingleResult(FIND_USER_BY_EMAIL_QUERY, email);
    }

    @Override
    public Optional<User> getRandomTrainer(){
        return executeForSingleResult(GET_RANDOM_TRAINER_QUERY);
    }

    @Override
    protected String getTableName() {
        return USER_TABLE;
    }

    @Override
    protected Object[] getFields(User user) {
        UserRole role = user.getRole();
        return new Object[] {
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getSecondName(),
                role.toString(),
                user.getDiscount()
        };
    }
}
