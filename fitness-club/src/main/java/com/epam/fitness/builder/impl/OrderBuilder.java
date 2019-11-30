package com.epam.fitness.builder.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * <p>Builds an instance of the {@link Order} class.</p>
 *
 * @see Builder
 * @see Order
 */
public class OrderBuilder implements Builder<Order> {

    private static final String ID_COLUMN = "id";
    private static final String BEGIN_DATE_COLUMN = "begin_date";
    private static final String END_DATE_COLUMN = "end_date";
    private static final String FEEDBACK_COLUMN = "feedback";
    private static final String PRICE_COLUMN = "price";
    private static final String TRAINER_ID_COLUMN = "trainer_id";
    private static final String CLIENT_ID_COLUMN = "client_id";
    private static final String NUTRITION_TYPE_COLUMN = "nutrition_type";

    /**
     * <p>Builds an instance of the {@link Order} class from
     * the supplied {@link ResultSet}.</p>
     *
     * @param resultSet a result set of parameters
     * @return a built order
     */
    @Override
    public Order build(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        int clientId = resultSet.getInt(CLIENT_ID_COLUMN);
        int trainerId = resultSet.getInt(TRAINER_ID_COLUMN);
        Date beginDate = resultSet.getTimestamp(BEGIN_DATE_COLUMN);
        Date endDate = resultSet.getTimestamp(END_DATE_COLUMN);
        String feedback = resultSet.getString(FEEDBACK_COLUMN);
        BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN);
        String nutritionTypeValue = resultSet.getString(NUTRITION_TYPE_COLUMN);
        NutritionType nutritionType = NutritionType.getNutritionType(nutritionTypeValue);
        return new Order(id, clientId, trainerId, beginDate, endDate, price, feedback, nutritionType);
    }
}