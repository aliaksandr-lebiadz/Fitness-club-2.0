package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    private static final String CLIENT_PARAMETER = "client";
    private static final String TRAINER_PARAMETER = "trainer";
    private static final String ID_PARAMETER = "id";

    public OrderDaoImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> findOrdersOfTrainerClient(int clientId, User trainer) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<Order> order = getRoot(criteriaQuery);
        Predicate trainerEquality = criteriaBuilder.equal(order.get(TRAINER_PARAMETER), trainer);
        Predicate clientIdEquality = criteriaBuilder.equal(order.get(CLIENT_PARAMETER).get(ID_PARAMETER), clientId);
        criteriaQuery
                .select(order)
                .where(criteriaBuilder.and(trainerEquality, clientIdEquality));
        TypedQuery<Order> query = getQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Order> findOrdersOfClient(User client) {
        return client.getOrders();
    }
}
