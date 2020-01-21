package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.service.api.GymMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymMembershipServiceImpl implements GymMembershipService {

    private Dao<GymMembership> gymMembershipDao;

    @Autowired
    public GymMembershipServiceImpl(Dao<GymMembership> gymMembershipDao){
        this.gymMembershipDao = gymMembershipDao;
    }

    @Override
    public List<GymMembership> getAll() {
        return gymMembershipDao.getAll();
    }
}