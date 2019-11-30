package com.epam.fitness.dao.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * <p>An implementation of the user dao interface to
 * provide access to the user entity in the MySql database.</p>
 *
 * @see User
 */
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String USER_TABLE = "fitness_user";
    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD_QUERY =
            "SELECT * FROM fitness_user WHERE email = ? AND password = ?";
    private static final String FIND_USERS_BY_TRAINER_ID_QUERY =
            "SELECT DISTINCT u.id, email, password, role, discount, first_name, second_name " +
                    "FROM fitness_user AS u " +
                    "JOIN client_order AS o ON u.id = o.client_id " +
                    "WHERE o.trainer_id = ?";
    private static final String SAVE_USER_QUERY = "INSERT INTO fitness_user" +
            "(id, email, password, first_name, second_name, role, discount) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE " +
            "id = VALUES(id)," +
            "email = VALUES(email)," +
            "password = VALUES(password)," +
            "first_name = VALUES(first_name)," +
            "second_name = VALUES(second_name)," +
            "role = VALUES(role)," +
            "discount = VALUES(discount)";
    private static final String GET_ALL_CLIENTS = "" +
            "SELECT * FROM fitness_user WHERE role = 'client'";

    public UserDaoImpl(Connection connection, Builder<User> builder){
        super(connection, builder);
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_EMAIL_AND_PASSWORD_QUERY, email, password);
    }

    @Override
    public List<User> findUsersByTrainerId(long trainerId) throws DaoException {
        return executeQuery(FIND_USERS_BY_TRAINER_ID_QUERY, trainerId);
    }

    @Override
    public List<User> getAllClients() throws DaoException {
        return executeQuery(GET_ALL_CLIENTS);
    }

    /**
     * <p>Inserts the user into the users table when
     * no user with the supplied id exists in the table and
     * updates the user otherwise.</p>
     *
     * @param user the user to save
     */
    @Override
    public void save(User user) throws DaoException {
        UserRole role = user.getRole();
        Object[] fields = {
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getSecondName(),
                role.toString(),
                user.getDiscount()
        };
        executeUpdate(SAVE_USER_QUERY, fields);
    }

    @Override
    protected String getTableName(){
        return USER_TABLE;
    }
}