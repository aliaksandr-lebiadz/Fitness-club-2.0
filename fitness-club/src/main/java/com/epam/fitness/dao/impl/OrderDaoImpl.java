package com.epam.fitness.dao.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.DaoException;

import java.sql.Connection;
import java.util.List;

/**
 * <p>An implementation of the order dao interface to provide access
 * to the order entity in the MySql database.</p>
 *
 * @see Order
 */
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    private static final String ORDER_TABLE = "client_order";
    private static final String SELECT_CLIENT_ORDERS_WITH_TRAINER_ID_QUERY =
            "SELECT * FROM client_order AS o JOIN fitness_user AS u ON o.client_id = u.id " +
                    "WHERE u.id = ? AND o.trainer_id = ?";
    private static final String SELECT_ORDERS_BY_CLIENT_ID =
            "SELECT * FROM client_order AS o JOIN fitness_user AS u ON o.client_id = u.id WHERE u.id = ?";
    private static final String SAVE_ORDER_QUERY = "INSERT INTO client_order " +
            "(id, client_id, begin_date, end_date, price, feedback, nutrition_type, trainer_id) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, " +
            "(SELECT id FROM fitness_user WHERE role = 'trainer' ORDER BY RAND() LIMIT 1)) " +
            "ON DUPLICATE KEY UPDATE " +
            "id = VALUES(id)," +
            "client_id = VALUES(client_id)," +
            "begin_date = VALUES(begin_date)," +
            "end_date = VALUES(end_date)," +
            "price = VALUES(price)," +
            "feedback = VALUES(feedback)," +
            "nutrition_type = VALUES(nutrition_type)";

    public OrderDaoImpl(Connection connection, Builder<Order> builder){
        super(connection, builder);
    }

    @Override
    public List<Order> findClientOrdersWithTrainerId(int clientId, int trainerId) throws DaoException{
        return executeQuery(SELECT_CLIENT_ORDERS_WITH_TRAINER_ID_QUERY, clientId, trainerId);
    }

    @Override
    public List<Order> findOrdersByClientId(int clientId) throws DaoException {
        return executeQuery(SELECT_ORDERS_BY_CLIENT_ID, clientId);
    }

    /**
     * <p>Inserts the order into the orders table when
     * no order with the supplied id exists in the table and
     * updates the order otherwise.</p>
     *
     * @param order the order to save
     */
    @Override
    public void save(Order order) throws DaoException {
        NutritionType nutritionType = order.getNutritionType();
        Object[] fields = {
                order.getId(),
                order.getClientId(),
                order.getBeginDate(),
                order.getEndDate(),
                order.getPrice(),
                order.getFeedback(),
                (nutritionType != null ? nutritionType.getValue() : null),
        };
        executeUpdate(SAVE_ORDER_QUERY, fields);
    }

    @Override
    protected String getTableName() {
        return ORDER_TABLE;
    }
}