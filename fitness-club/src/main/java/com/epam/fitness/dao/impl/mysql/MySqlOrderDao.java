package com.epam.fitness.dao.impl.mysql;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.impl.AbstractOrderDao;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;

import java.sql.Connection;

/**
 * <p>An implementation of the order dao interface to provide access
 * to the order entity in the MySql database.</p>
 *
 * @see Order
 */
public class MySqlOrderDao extends AbstractOrderDao {

    private static final String SAVE_ORDER_QUERY = "INSERT INTO client_order " +
            "(id, client_id, begin_date, end_date, price, feedback, nutrition_type, trainer_id) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, " +
            "(SELECT id FROM fitness_user WHERE role = 'trainer' ORDER BY RAND() LIMIT 1)) " +
            "ON DUPLICATE KEY UPDATE " +
            "client_id = VALUES(client_id)," +
            "begin_date = VALUES(begin_date)," +
            "end_date = VALUES(end_date)," +
            "price = VALUES(price)," +
            "feedback = VALUES(feedback)," +
            "nutrition_type = VALUES(nutrition_type)";

    public MySqlOrderDao(Connection connection, Builder<Order> builder){
        super(connection, builder);
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_ORDER_QUERY;
    }

    @Override
    protected Object[] getFields(Order order) {
        NutritionType nutritionType = order.getNutritionType();
        return new Object[]{
                order.getId(),
                order.getClientId(),
                order.getBeginDate(),
                order.getEndDate(),
                order.getPrice(),
                order.getFeedback(),
                (nutritionType != null ? nutritionType.getValue() : null)
        };
    }
}