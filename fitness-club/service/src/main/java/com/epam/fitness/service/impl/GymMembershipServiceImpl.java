package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.service.api.GymMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GymMembershipServiceImpl implements GymMembershipService {

    private final Dao<GymMembership> dao;
    private final DtoMapper<GymMembership, GymMembershipDto> mapper;

    @Autowired
    public GymMembershipServiceImpl(Dao<GymMembership> dao, DtoMapper<GymMembership, GymMembershipDto> mapper){
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public List<GymMembershipDto> getAll() {
        List<GymMembership> gymMemberships = dao.getAll();
        return gymMemberships.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}