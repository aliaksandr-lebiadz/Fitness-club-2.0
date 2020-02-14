package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.entity.GymMembership;
import org.springframework.stereotype.Repository;

@Repository
public class GymMembershipDaoImpl extends AbstractDao<GymMembership> {

    public GymMembershipDaoImpl() {
        super(GymMembership.class);
    }

}
