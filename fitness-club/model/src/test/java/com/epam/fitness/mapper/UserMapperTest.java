package com.epam.fitness.mapper;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapperTest {

    private static final String ID_COLUMN = "id";
    private static final String EMAIL_COLUMN = "email";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ROLE_COLUMN = "role";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String SECOND_NAME_COLUMN = "second_name";
    private static final String DISCOUNT_COLUMN = "discount";

    private UserMapper mapper = new UserMapper();

    @Test
    public void testBuildShouldReturnBuiltUserEntity() throws SQLException {
        //given
        final int id = 1;
        final String email = "email@mail.ru";
        final String password = "MyPass";
        final String userRoleValue = "admin";
        final String firstName = "Alex";
        final String secondName = "Lep";
        final int discount = 50;
        UserRole role = UserRole.valueOf(userRoleValue.toUpperCase());
        User expected = new User(id, email, password, role, firstName, secondName, discount);

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(ID_COLUMN)).thenReturn(id);
        Mockito.when(resultSet.getString(EMAIL_COLUMN)).thenReturn(email);
        Mockito.when(resultSet.getString(PASSWORD_COLUMN)).thenReturn(password);
        Mockito.when(resultSet.getString(ROLE_COLUMN)).thenReturn(userRoleValue);
        Mockito.when(resultSet.getString(FIRST_NAME_COLUMN)).thenReturn(firstName);
        Mockito.when(resultSet.getString(SECOND_NAME_COLUMN)).thenReturn(secondName);
        Mockito.when(resultSet.getInt(DISCOUNT_COLUMN)).thenReturn(discount);

        //when
        User actual = mapper.mapRow(resultSet, ArgumentMatchers.anyInt());

        //then
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getRole(), actual.getRole());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getSecondName(), actual.getSecondName());
        Assert.assertEquals(expected.getDiscount(), actual.getDiscount());
    }

}