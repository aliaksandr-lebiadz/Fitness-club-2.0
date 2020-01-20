package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract  class AbstractOrderDao extends AbstractDao<Order> implements OrderDao {

    private static final String ORDER_TABLE = "client_order";
    private static final String SELECT_CLIENT_ORDERS_WITH_TRAINER_ID_QUERY =
            "SELECT * FROM client_order AS o JOIN fitness_user AS u ON o.client_id = u.id " +
                    "WHERE u.id = ? AND o.trainer_id = ?";
    private static final String SELECT_ORDERS_BY_CLIENT_ID =
            "SELECT * FROM client_order AS o JOIN fitness_user AS u ON o.client_id = u.id WHERE u.id = ?";

    public AbstractOrderDao(JdbcTemplate jdbcTemplate, RowMapper<Order> rowMapper){
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
    protected String getTableName() {
        return ORDER_TABLE;
    }
}
