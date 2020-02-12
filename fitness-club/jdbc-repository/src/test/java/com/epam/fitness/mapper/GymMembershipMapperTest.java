package com.epam.fitness.mapper;

import com.epam.fitness.entity.GymMembership;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.fitness.mapper.GymMembershipMapper.ID_COLUMN;
import static com.epam.fitness.mapper.GymMembershipMapper.MONTHS_AMOUNT_COLUMN;
import static com.epam.fitness.mapper.GymMembershipMapper.PRICE_COLUMN;
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

    @Before
    public void createMocks() throws SQLException{
        when(resultSet.getInt(ID_COLUMN)).thenReturn(ID);
        when(resultSet.getInt(MONTHS_AMOUNT_COLUMN)).thenReturn(MONTHS_AMOUNT);
        when(resultSet.getBigDecimal(PRICE_COLUMN)).thenReturn(PRICE);
    }

    @Test
    public void testBuildShouldReturnBuiltGymMembershipEntity() throws SQLException {
        //given
        GymMembership expected = new GymMembership(ID, MONTHS_AMOUNT, PRICE);

        //when
        GymMembership actual = mapper.mapRow(resultSet, ROW_INDEX);

        //then
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getMonthsAmount(), actual.getMonthsAmount());
        assertThat(actual.getPrice(), comparesEqualTo(expected.getPrice()));
    }

    @After
    public void verifyMocks() throws SQLException{
        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getInt(MONTHS_AMOUNT_COLUMN);
        verify(resultSet, times(1)).getBigDecimal(PRICE_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

}