package com.epam.fitness.service.api;

import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.exception.ServiceException;

import java.util.List;

public interface GymMembershipService {

    List<GymMembership> getAll() throws ServiceException;

}