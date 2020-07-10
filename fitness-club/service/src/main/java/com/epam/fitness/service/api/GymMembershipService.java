package com.epam.fitness.service.api;

import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.exception.ServiceException;

import java.util.List;

public interface GymMembershipService {

    List<GymMembershipDto> getAll() throws ServiceException;

}