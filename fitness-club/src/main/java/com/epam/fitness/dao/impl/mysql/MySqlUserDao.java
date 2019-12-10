package com.epam.fitness.dao.impl.mysql;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.impl.AbstractUserDao;
import com.epam.fitness.entity.user.User;

import java.sql.Connection;

/**
 * <p>An implementation of the user dao interface to
 * provide access to the user entity in the MySql database.</p>
 *
 * @see User
 */
public class MySqlUserDao extends AbstractUserDao {

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

    public MySqlUserDao(Connection connection, Builder<User> builder){
        super(connection, builder);
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_USER_QUERY;
    }
}