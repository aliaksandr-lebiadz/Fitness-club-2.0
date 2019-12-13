package com.epam.fitness.dao.impl.postgresql;


import com.epam.fitness.dao.impl.AbstractOrderDao;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>An implementation of the order dao interface to provide access
 * to the order entity in the PostgreSql database.</p>
 *
 * @see Order
 */
@Repository
public class PostgreSqlOrderDao extends AbstractOrderDao {

    private static final String SAVE_ORDER_QUERY = "INSERT INTO client_order " +
            "(id, client_id, begin_date, end_date, price, feedback, nutrition_type, trainer_id) " +
            "VALUES(COALESCE(?, (SELECT MAX(id) FROM client_order as uid) + 1, 1), ?, ?, ?, ?, ?, ?::nutrition_type, " +
            "(SELECT id FROM fitness_user WHERE role = 'trainer' ORDER BY RANDOM() LIMIT 1)) " +
            "ON CONFLICT(id) DO UPDATE SET " +
            "client_id = EXCLUDED.client_id," +
            "begin_date = EXCLUDED.begin_date," +
            "end_date = EXCLUDED.end_date," +
            "price = EXCLUDED.price," +
            "feedback = EXCLUDED.feedback," +
            "nutrition_type = EXCLUDED.nutrition_type::nutrition_type";

    @Autowired
    public PostgreSqlOrderDao(JdbcTemplate jdbcTemplate, RowMapper<Order> rowMapper) {
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
                convertToTimestamp(order.getBeginDate()),
                convertToTimestamp(order.getEndDate()),
                order.getPrice(),
                order.getFeedback(),
                (nutritionType != null ? nutritionType.getValue() : null)
        };
    }

    private Timestamp convertToTimestamp(Date date){
        long time = date.getTime();
        return new Timestamp(time);
    }
}
