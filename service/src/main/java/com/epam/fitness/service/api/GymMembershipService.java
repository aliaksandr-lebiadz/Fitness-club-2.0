package com.epam.fitness.service.api;

import com.epam.fitness.entity.GymMembershipDto;

import java.util.List;

public interface GymMembershipService {

    List<GymMembershipDto> getAll();

}