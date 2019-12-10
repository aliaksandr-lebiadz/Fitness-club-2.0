package com.epam.fitness.dao.api;

import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.DaoException;

import java.util.List;

/**
 * <p>An interface specified for an order entity
 * to provide an access to it.</p>
 *
 * @see Order
 */
public interface OrderDao extends Dao<Order> {

    /**
     * <p>Finds orders with the supplied client's id and trainer's id.</p>
     *
     * @param clientId client's id
     * @param trainerId trainer's id
     * @return list of found orders
     */
    List<Order> findClientOrdersWithTrainerId(int clientId, int trainerId) throws DaoException;

    /**
     * <p>Finds orders with supplied client's id.</p>
     *
     * @param clientId client's id
     * @return list of found orders
     */
    List<Order> findOrdersByClientId(int clientId) throws DaoException;

}