package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>An implementation of the order dao interface to provide access
 * to the order entity in the PostgreSql database.</p>
 *
 * @see Order
 */
@Repository
public class PostgreSqlOrderDao extends AbstractDao<Order> implements OrderDao {

    private static final String ORDER_TABLE = "client_order";
    private static final String SELECT_CLIENT_ORDERS_WITH_TRAINER_ID_QUERY =
            "SELECT * FROM client_order AS o JOIN fitness_user AS u ON o.client_id = u.id " +
                    "WHERE u.id = ? AND o.trainer_id = ?";
    private static final String SELECT_ORDERS_BY_CLIENT_ID =
            "SELECT * FROM client_order AS o JOIN fitness_user AS u ON o.client_id = u.id WHERE u.id = ?";
    private static final String SAVE_ORDER_QUERY = "INSERT INTO client_order " +
            "(id, client_id, begin_date, end_date, price, feedback, nutrition_type, trainer_id) " +
            "VALUES(COALESCE(?, (SELECT MAX(id) FROM client_order as uid) + 1, 1), ?, ?, ?, ?, ?, ?, ?) " +
            "ON CONFLICT(id) DO UPDATE SET " +
            "client_id = EXCLUDED.client_id," +
            "begin_date = EXCLUDED.begin_date," +
            "end_date = EXCLUDED.end_date," +
            "price = EXCLUDED.price," +
            "feedback = EXCLUDED.feedback," +
            "nutrition_type = EXCLUDED.nutrition_type," +
            "trainer_id = EXCLUDED.trainer_id";

    @Autowired
    public PostgreSqlOrderDao(JdbcTemplate jdbcTemplate, RowMapper<Order> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public List<Order> findOrdersOfTrainerClient(int clientId, User trainer) {
        int trainerId = trainer.getId();
        return executeQuery(SELECT_CLIENT_ORDERS_WITH_TRAINER_ID_QUERY, clientId, trainerId);
    }

    @Override
    public List<Order> findOrdersOfClient(User client) {
        int clientId = client.getId();
        return executeQuery(SELECT_ORDERS_BY_CLIENT_ID, clientId);
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
                (nutritionType != null ? nutritionType.toString() : null),
                order.getTrainerId()
        };
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_ORDER_QUERY;
    }

    @Override
    protected String getTableName() {
        return ORDER_TABLE;
    }

    private Timestamp convertToTimestamp(Date date){
        long time = date.getTime();
        return new Timestamp(time);
    }
}
