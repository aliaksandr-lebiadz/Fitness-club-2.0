package com.epam.fitness.service.impl;

import com.epam.fitness.dao.factory.DaoFactory;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.exception.DaoException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.GymMembershipDao;
import com.epam.fitness.service.api.GymMembershipService;

import java.util.List;

public class GymMembershipServiceImpl implements GymMembershipService {

    private GymMembershipDao gymMembershipDao;

    public GymMembershipServiceImpl(DaoFactory factory){
        this.gymMembershipDao = factory.createGymMembershipDao();
    }

    @Override
    public List<GymMembership> getAll() throws ServiceException {
        try{
            return gymMembershipDao.getAll();
        } catch(DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}