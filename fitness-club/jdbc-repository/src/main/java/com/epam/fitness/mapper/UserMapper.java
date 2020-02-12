package com.epam.fitness.mapper;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    /*package-private*/ static final String EMAIL_COLUMN = "email";
    /*package-private*/ static final String PASSWORD_COLUMN = "password";
    /*package-private*/ static final String ROLE_COLUMN = "role";
    /*package-private*/ static final String DISCOUNT_COLUMN = "discount";
    /*package-private*/ static final String ID_COLUMN = "id";
    /*package-private*/ static final String FIRST_NAME_COLUMN = "first_name";
    /*package-private*/ static final String SECOND_NAME_COLUMN = "second_name";

    @Override
    public User mapRow(ResultSet resultSet, int index) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        String email = resultSet.getString(EMAIL_COLUMN);
        String password = resultSet.getString(PASSWORD_COLUMN);
        String roleValue = resultSet.getString(ROLE_COLUMN);
        UserRole role = UserRole.valueOf(roleValue.toUpperCase());
        String firstName = resultSet.getString(FIRST_NAME_COLUMN);
        String secondName = resultSet.getString(SECOND_NAME_COLUMN);
        int discount = resultSet.getInt(DISCOUNT_COLUMN);
        return new User(id, email, password, role, firstName, secondName, discount);
    }
}
