package com.epam.fitness.dao.impl.mysql;

import com.epam.fitness.dao.impl.AbstractOrderDao;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>An implementation of the order dao interface to provide access
 * to the order entity in the MySql database.</p>
 *
 * @see Order
 */
@Repository
public class MySqlOrderDao extends AbstractOrderDao {

    private static final String SAVE_ORDER_QUERY = "INSERT INTO client_order " +
            "(id, client_id, begin_date, end_date, price, feedback, nutrition_type, trainer_id) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE " +
            "client_id = VALUES(client_id)," +
            "begin_date = VALUES(begin_date)," +
            "end_date = VALUES(end_date)," +
            "price = VALUES(price)," +
            "feedback = VALUES(feedback)," +
            "nutrition_type = VALUES(nutrition_type)," +
            "trainer_id = VALUES(trainer_id)";

    @Autowired
    public MySqlOrderDao(JdbcTemplate jdbcTemplate, RowMapper<Order> rowMapper){
        super(jdbcTemplate, rowMapper);
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
                (nutritionType != null ? nutritionType.toString() : null),
                order.getTrainerId()
        };
    }
}