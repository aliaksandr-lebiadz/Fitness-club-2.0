package com.epam.fitness.dao.impl.postgresql;

import com.epam.fitness.dao.impl.AbstractUserDao;
import com.epam.fitness.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PostgreSqlUserDao extends AbstractUserDao {

    private static final String SAVE_USER_QUERY = "INSERT INTO fitness_user" +
            "(id, email, password, first_name, second_name, role, discount) " +
            "VALUES(COALESCE(?, (SELECT MAX(id) FROM client_order as uid) + 1, 1), ?, ?, ?, ?, ?::user_role, ?) " +
            "ON CONFLICT(id) DO UPDATE SET " +
            "email = EXCLUDED.email," +
            "password = EXCLUDED.password," +
            "first_name = EXCLUDED.first_name," +
            "second_name = EXCLUDED.second_name," +
            "role = EXCLUDED.role::user_role," +
            "discount = EXCLUDED.discount";

    @Autowired
    public PostgreSqlUserDao(JdbcTemplate jdbcTemplate, RowMapper<User> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_USER_QUERY;
    }
}
