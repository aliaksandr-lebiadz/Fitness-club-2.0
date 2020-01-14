package com.epam.fitness.mapper;

import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class OrderMapperTest {

    private static final String ID_COLUMN = "id";
    private static final String CLIENT_ID_COLUMN = "client_id";
    private static final String TRAINER_ID_COLUMN = "trainer_id";
    private static final String BEGIN_DATE_COLUMN = "begin_date";
    private static final String END_DATE_COLUMN = "end_date";
    private static final String FEEDBACK_COLUMN = "feedback";
    private static final String PRICE_COLUMN = "price";
    private static final String NUTRITION_TYPE_COLUMN = "nutrition_type";

    private OrderMapper mapper = new OrderMapper();

    @Test
    public void testBuildShouldReturnBuiltOrderEntity() throws SQLException {
        //given
        final int id = 5;
        final int clientId = 10;
        final int trainerId = 3;
        final Timestamp beginDate = Timestamp.valueOf("2019-10-12 18:48:05");
        final Timestamp endDate = Timestamp.valueOf("2020-04-12 18:48:05");
        final String feedback = "MyFeedback";
        final BigDecimal price = BigDecimal.valueOf(111.5);
        final String nutritionTypeValue = "low calorie";
        NutritionType nutritionType = NutritionType.getNutritionType(nutritionTypeValue);
        Order expected = Order.createBuilder()
                .setId(id)
                .setClientId(clientId)
                .setTrainerId(trainerId)
                .setBeginDate(beginDate)
                .setEndDate(endDate)
                .setFeedback(feedback)
                .setPrice(price)
                .setNutritionType(nutritionType)
                .build();

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(ID_COLUMN)).thenReturn(id);
        Mockito.when(resultSet.getInt(CLIENT_ID_COLUMN)).thenReturn(clientId);
        Mockito.when(resultSet.getInt(TRAINER_ID_COLUMN)).thenReturn(trainerId);
        Mockito.when(resultSet.getTimestamp(BEGIN_DATE_COLUMN)).thenReturn(beginDate);
        Mockito.when(resultSet.getTimestamp(END_DATE_COLUMN)).thenReturn(endDate);
        Mockito.when(resultSet.getString(FEEDBACK_COLUMN)).thenReturn(feedback);
        Mockito.when(resultSet.getBigDecimal(PRICE_COLUMN)).thenReturn(price);
        Mockito.when(resultSet.getString(NUTRITION_TYPE_COLUMN)).thenReturn(nutritionTypeValue);

        //when
        Order actual = mapper.mapRow(resultSet, ArgumentMatchers.anyInt());

        //then
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getClientId(), actual.getClientId());
        Assert.assertEquals(expected.getTrainerId(), actual.getTrainerId());
        Assert.assertEquals(expected.getBeginDate(), actual.getBeginDate());
        Assert.assertEquals(expected.getEndDate(), actual.getEndDate());
        Assert.assertEquals(expected.getFeedback(), actual.getFeedback());
        assertThat(actual.getPrice(), Matchers.comparesEqualTo(expected.getPrice()));
        Assert.assertEquals(expected.getNutritionType(), actual.getNutritionType());
    }

}