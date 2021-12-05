package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.order.Order;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssignmentDaoImpl extends AbstractDao<Assignment> implements AssignmentDao {

    @Autowired
    public AssignmentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Assignment.class);
    }

    @Override
    public List<Assignment> getAllByOrder(Order order) {
        return order.getAssignments();
    }
}
