package com.epam.fitness.builder.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>Builds an instance of the {@link User} class.</p>
 *
 * @see Builder
 * @see User
 */
public class UserBuilder implements Builder<User> {

    private static final String EMAIL_COLUMN = "email";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ROLE_COLUMN = "role";
    private static final String DISCOUNT_COLUMN = "discount";
    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String SECOND_NAME_COLUMN = "second_name";

    /**
     * <p>Builds an instance of the {@link User} class from
     * the supplied {@link ResultSet}.</p>
     *
     * @param resultSet a result set of parameters
     * @return a built user
     */
    @Override
    public User build(ResultSet resultSet) throws SQLException {
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