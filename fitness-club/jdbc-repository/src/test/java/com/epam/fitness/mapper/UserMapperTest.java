package com.epam.fitness.mapper;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.fitness.mapper.UserMapper.ID_COLUMN;
import static com.epam.fitness.mapper.UserMapper.EMAIL_COLUMN;
import static com.epam.fitness.mapper.UserMapper.PASSWORD_COLUMN;
import static com.epam.fitness.mapper.UserMapper.ROLE_COLUMN;
import static com.epam.fitness.mapper.UserMapper.FIRST_NAME_COLUMN;
import static com.epam.fitness.mapper.UserMapper.SECOND_NAME_COLUMN;
import static com.epam.fitness.mapper.UserMapper.DISCOUNT_COLUMN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;

public class UserMapperTest {

    private static final int ID = 1;
    private static final String EMAIL = "email@mail.ru";
    private static final String PASSWORD = "MyPass";
    private static final String USER_ROLE_VALUE = "ADMIN";
    private static final String FIRST_NAME = "Alex";
    private static final String SECOND_NAME = "Lep";
    private static final int DISCOUNT = 50;
    private static final int ROW_INDEX = 15;

    private UserMapper mapper = new UserMapper();
    private ResultSet resultSet = mock(ResultSet.class);

    @Test
    public void buildWhenValidResultSetSupplied() throws SQLException {
        //given
        when(resultSet.getInt(ID_COLUMN)).thenReturn(ID);
        when(resultSet.getString(EMAIL_COLUMN)).thenReturn(EMAIL);
        when(resultSet.getString(PASSWORD_COLUMN)).thenReturn(PASSWORD);
        when(resultSet.getString(ROLE_COLUMN)).thenReturn(USER_ROLE_VALUE);
        when(resultSet.getString(FIRST_NAME_COLUMN)).thenReturn(FIRST_NAME);
        when(resultSet.getString(SECOND_NAME_COLUMN)).thenReturn(SECOND_NAME);
        when(resultSet.getInt(DISCOUNT_COLUMN)).thenReturn(DISCOUNT);
        UserRole role = UserRole.valueOf(USER_ROLE_VALUE);
        User expected = new User(ID, EMAIL, PASSWORD, role, FIRST_NAME, SECOND_NAME, DISCOUNT);

        //when
        User actual = mapper.mapRow(resultSet, ROW_INDEX);

        //then
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getRole(), actual.getRole());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getSecondName(), actual.getSecondName());
        assertEquals(expected.getDiscount(), actual.getDiscount());

        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getString(EMAIL_COLUMN);
        verify(resultSet, times(1)).getString(PASSWORD_COLUMN);
        verify(resultSet, times(1)).getString(ROLE_COLUMN);
        verify(resultSet, times(1)).getString(FIRST_NAME_COLUMN);
        verify(resultSet, times(1)).getString(SECOND_NAME_COLUMN);
        verify(resultSet, times(1)).getInt(DISCOUNT_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

    @Test(expected = SQLException.class)
    public void buildWithSqlExceptionWhenInvalidResultSetSupplied() throws SQLException {
        //given
        when(resultSet.getInt(ID_COLUMN)).thenReturn(ID);
        when(resultSet.getString(EMAIL_COLUMN)).thenThrow(SQLException.class);

        //when
        mapper.mapRow(resultSet, ROW_INDEX);

        //then
        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getString(EMAIL_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

}