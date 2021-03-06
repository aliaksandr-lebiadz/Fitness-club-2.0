package com.epam.fitness.mapper;

import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Component
public class OrderMapper implements RowMapper<Order> {

    /*package-private*/ static final String ID_COLUMN = "id";
    /*package-private*/ static final String BEGIN_DATE_COLUMN = "begin_date";
    /*package-private*/ static final String END_DATE_COLUMN = "end_date";
    /*package-private*/ static final String FEEDBACK_COLUMN = "feedback";
    /*package-private*/ static final String PRICE_COLUMN = "price";
    /*package-private*/ static final String TRAINER_ID_COLUMN = "trainer_id";
    /*package-private*/ static final String CLIENT_ID_COLUMN = "client_id";
    /*package-private*/ static final String NUTRITION_TYPE_COLUMN = "nutrition_type";

    @Override
    public Order mapRow(ResultSet resultSet, int index) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        int clientId = resultSet.getInt(CLIENT_ID_COLUMN);
        int trainerId = resultSet.getInt(TRAINER_ID_COLUMN);
        Date beginDate = resultSet.getTimestamp(BEGIN_DATE_COLUMN);
        Date endDate = resultSet.getTimestamp(END_DATE_COLUMN);
        String feedback = resultSet.getString(FEEDBACK_COLUMN);
        BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN);
        String nutritionTypeValue = resultSet.getString(NUTRITION_TYPE_COLUMN);
        NutritionType nutritionType = nutritionTypeValue != null ? NutritionType.valueOf(nutritionTypeValue) : null;
        return Order.createBuilder()
                .setId(id)
                .setClient(clientId)
                .setTrainer(trainerId)
                .setBeginDate(beginDate)
                .setEndDate(endDate)
                .setPrice(price)
                .setFeedback(feedback)
                .setNutritionType(nutritionType)
                .build();
    }

}
