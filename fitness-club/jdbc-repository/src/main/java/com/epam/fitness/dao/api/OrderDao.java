package com.epam.fitness.dao.api;

import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;

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
     * @return list of found orders
     */
    List<Order> findOrdersOfTrainerClient(int clientId, User trainer);

    /**
     * <p>Finds orders with supplied client's id.</p>
     *
     * @return list of found orders
     */
    List<Order> findOrdersOfClient(User client);

}