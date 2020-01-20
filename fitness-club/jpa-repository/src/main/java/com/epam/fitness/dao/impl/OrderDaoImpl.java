package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    private static final String CLIENT_PARAMETER = "client";
    private static final String TRAINER_PARAMETER = "trainer";
    private static final String ID_PARAMETER = "id";

    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Order.class);
    }


    @Override
    public List<Order> findOrdersOfTrainerClient(int clientId, User trainer) {
        return trainer.getOrders()
                .stream()
                .filter(order -> order.getClient().getId() == clientId)
                .collect(Collectors.toList());
        /*CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<Order> order = getRoot(criteriaQuery);
        Predicate trainerEquality = criteriaBuilder.equal(order.get(TRAINER_PARAMETER), trainer);
        Predicate clientIdEquality = criteriaBuilder.equal(order.get(CLIENT_PARAMETER).get(ID_PARAMETER), clientId);
        criteriaQuery
                .select(order)
                .where(criteriaBuilder.and(trainerEquality, clientIdEquality));
        Query<Order> query = getQuery(criteriaQuery);
        return query.getResultList();*/
    }

    @Override
    public List<Order> findOrdersOfClient(User client) {
        return client.getOrders();
    }
}
