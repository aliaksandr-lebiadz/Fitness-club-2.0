package com.epam.fitness.mapper;

import com.epam.fitness.entity.GymMembership;

import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.fitness.mapper.GymMembershipMapper.ID_COLUMN;
import static com.epam.fitness.mapper.GymMembershipMapper.MONTHS_AMOUNT_COLUMN;
import static com.epam.fitness.mapper.GymMembershipMapper.PRICE_COLUMN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

public class GymMembershipMapperTest {

    private static final int ID = 1;
    private static final int MONTHS_AMOUNT = 6;
    private static final BigDecimal PRICE = BigDecimal.valueOf(166.71);
    private static final int ROW_INDEX = 3;

    private GymMembershipMapper mapper = new GymMembershipMapper();
    private ResultSet resultSet = mock(ResultSet.class);

    @Test
    public void buildWhenValidResultSetSupplied() throws SQLException {
        //given
        when(resultSet.getInt(ID_COLUMN)).thenReturn(ID);
        when(resultSet.getInt(MONTHS_AMOUNT_COLUMN)).thenReturn(MONTHS_AMOUNT);
        when(resultSet.getBigDecimal(PRICE_COLUMN)).thenReturn(PRICE);
        GymMembership expected = new GymMembership(ID, MONTHS_AMOUNT, PRICE);

        //when
        GymMembership actual = mapper.mapRow(resultSet, ROW_INDEX);

        //then
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMonthsAmount(), actual.getMonthsAmount());
        assertThat(actual.getPrice(), comparesEqualTo(expected.getPrice()));

        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getInt(MONTHS_AMOUNT_COLUMN);
        verify(resultSet, times(1)).getBigDecimal(PRICE_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

    @Test(expected = SQLException.class)
    public void buildWithSqlExceptionWhenInvalidResultSetSupplied() throws SQLException {
        //given
        when(resultSet.getInt(ID_COLUMN)).thenReturn(ID);
        when(resultSet.getInt(MONTHS_AMOUNT_COLUMN)).thenReturn(MONTHS_AMOUNT);
        when(resultSet.getBigDecimal(PRICE_COLUMN)).thenThrow(SQLException.class);

        //when
        mapper.mapRow(resultSet, ROW_INDEX);

        //then

        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getInt(MONTHS_AMOUNT_COLUMN);
        verify(resultSet, times(1)).getBigDecimal(PRICE_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

}