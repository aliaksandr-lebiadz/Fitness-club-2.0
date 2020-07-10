package com.epam.fitness.mapper;

import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.epam.fitness.mapper.OrderMapper.ID_COLUMN;
import static com.epam.fitness.mapper.OrderMapper.CLIENT_ID_COLUMN;
import static com.epam.fitness.mapper.OrderMapper.TRAINER_ID_COLUMN;
import static com.epam.fitness.mapper.OrderMapper.BEGIN_DATE_COLUMN;
import static com.epam.fitness.mapper.OrderMapper.END_DATE_COLUMN;
import static com.epam.fitness.mapper.OrderMapper.FEEDBACK_COLUMN;
import static com.epam.fitness.mapper.OrderMapper.PRICE_COLUMN;
import static com.epam.fitness.mapper.OrderMapper.NUTRITION_TYPE_COLUMN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

public class OrderMapperTest {

    private static final int ID = 5;
    private static final int CLIENT_ID = 10;
    private static final int TRAINER_ID = 3;
    private static final Timestamp BEGIN_DATE = Timestamp.valueOf("2019-10-12 18:48:05");
    private static final Timestamp END_DATE = Timestamp.valueOf("2020-04-12 18:48:05");
    private static final String FEEDBACK = "MyFeedback";
    private static final BigDecimal PRICE = BigDecimal.valueOf(111.5);
    private static final String NUTRITION_TYPE_VALUE = "LOW_CALORIE";
    private static final int ROW_INDEX = 10;

    private OrderMapper mapper = new OrderMapper();
    private ResultSet resultSet = mock(ResultSet.class);

    @Test
    public void buildWhenValidResultSetSupplied() throws SQLException {
        //given
        when(resultSet.getInt(ID_COLUMN)).thenReturn(ID);
        when(resultSet.getInt(CLIENT_ID_COLUMN)).thenReturn(CLIENT_ID);
        when(resultSet.getInt(TRAINER_ID_COLUMN)).thenReturn(TRAINER_ID);
        when(resultSet.getTimestamp(BEGIN_DATE_COLUMN)).thenReturn(BEGIN_DATE);
        when(resultSet.getTimestamp(END_DATE_COLUMN)).thenReturn(END_DATE);
        when(resultSet.getString(FEEDBACK_COLUMN)).thenReturn(FEEDBACK);
        when(resultSet.getBigDecimal(PRICE_COLUMN)).thenReturn(PRICE);
        when(resultSet.getString(NUTRITION_TYPE_COLUMN)).thenReturn(NUTRITION_TYPE_VALUE);

        NutritionType nutritionType = NutritionType.valueOf(NUTRITION_TYPE_VALUE);
        Order expected = Order.createBuilder()
                .setId(ID)
                .setClient(CLIENT_ID)
                .setTrainer(TRAINER_ID)
                .setBeginDate(BEGIN_DATE)
                .setEndDate(END_DATE)
                .setFeedback(FEEDBACK)
                .setPrice(PRICE)
                .setNutritionType(nutritionType)
                .build();

        //when
        Order actual = mapper.mapRow(resultSet, ROW_INDEX);

        //then
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getClientId(), actual.getClientId());
        assertEquals(expected.getTrainerId(), actual.getTrainerId());
        assertEquals(expected.getBeginDate(), actual.getBeginDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
        assertEquals(expected.getFeedback(), actual.getFeedback());
        assertThat(actual.getPrice(), comparesEqualTo(expected.getPrice()));
        assertEquals(expected.getNutritionType(), actual.getNutritionType());

        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getInt(CLIENT_ID_COLUMN);
        verify(resultSet, times(1)).getInt(TRAINER_ID_COLUMN);
        verify(resultSet, times(1)).getTimestamp(BEGIN_DATE_COLUMN);
        verify(resultSet, times(1)).getTimestamp(END_DATE_COLUMN);
        verify(resultSet, times(1)).getString(FEEDBACK_COLUMN);
        verify(resultSet, times(1)).getBigDecimal(PRICE_COLUMN);
        verify(resultSet, times(1)).getString(NUTRITION_TYPE_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

    @Test(expected = SQLException.class)
    public void buildWithSqlExceptionWhenInvalidResultSetSupplied() throws SQLException {
        //given
        when(resultSet.getInt(ID_COLUMN)).thenReturn(ID);
        when(resultSet.getInt(CLIENT_ID_COLUMN)).thenReturn(CLIENT_ID);
        when(resultSet.getInt(TRAINER_ID_COLUMN)).thenThrow(SQLException.class);

        //when
        mapper.mapRow(resultSet, ROW_INDEX);

        //then
        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getInt(CLIENT_ID_COLUMN);
        verify(resultSet, times(1)).getInt(TRAINER_ID_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

    @Test
    public void buildWhenNullNutritionTypeSupplied() throws SQLException{
        //given
        when(resultSet.getString(NUTRITION_TYPE_COLUMN)).thenReturn(null);

        //when
        Order actual = mapper.mapRow(resultSet, ROW_INDEX);

        //then
        assertNotNull(actual);
        assertNull(actual.getNutritionType());
    }

}