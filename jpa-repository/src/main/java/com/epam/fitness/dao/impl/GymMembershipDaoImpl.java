package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.entity.GymMembership;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GymMembershipDaoImpl extends AbstractDao<GymMembership> {

    @Autowired
    public GymMembershipDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, GymMembership.class);
    }

}
