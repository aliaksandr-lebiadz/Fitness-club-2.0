package com.epam.fitness.mapper;

import com.epam.fitness.entity.GymMembership;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class GymMembershipMapperTest {

    private static final String ID_COLUMN = "id";
    private static final String MONTHS_AMOUNT_COLUMN = "months_amount";
    private static final String PRICE_COLUMN = "price";

    private GymMembershipMapper mapper = new GymMembershipMapper();

    @Test
    public void testBuildShouldReturnBuiltGymMembershipEntity() throws SQLException {
        //given
        final int id = 1;
        final int monthsAmount = 6;
        final BigDecimal price = BigDecimal.valueOf(166.71);
        GymMembership expected = new GymMembership(id, monthsAmount, price);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(ID_COLUMN)).thenReturn(id);
        when(resultSet.getInt(MONTHS_AMOUNT_COLUMN)).thenReturn(monthsAmount);
        when(resultSet.getBigDecimal(PRICE_COLUMN)).thenReturn(price);

        //when
        GymMembership actual = mapper.mapRow(resultSet, anyInt());

        //then
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getMonthsAmount(), actual.getMonthsAmount());
        assertThat(actual.getPrice(), Matchers.comparesEqualTo(expected.getPrice()));
    }

}