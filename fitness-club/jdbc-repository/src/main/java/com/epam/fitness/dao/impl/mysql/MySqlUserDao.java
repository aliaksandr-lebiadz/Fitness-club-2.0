package com.epam.fitness.dao.impl.mysql;

import com.epam.fitness.dao.impl.AbstractUserDao;
import com.epam.fitness.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>An implementation of the user dao interface to
 * provide access to the user entity in the MySql database.</p>
 *
 * @see User
 */
@Repository
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

    @Autowired
    public MySqlUserDao(JdbcTemplate jdbcTemplate, RowMapper<User> rowMapper){
        super(jdbcTemplate, rowMapper);
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_USER_QUERY;
    }
}