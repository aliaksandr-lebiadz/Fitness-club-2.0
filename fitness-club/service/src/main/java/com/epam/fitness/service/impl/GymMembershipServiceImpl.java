package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.service.api.GymMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GymMembershipServiceImpl implements GymMembershipService {

    private Dao<GymMembership> dao;
    private DtoMapper<GymMembership, GymMembershipDto> mapper;

    @Autowired
    public GymMembershipServiceImpl(Dao<GymMembership> dao,
                                    DtoMapper<GymMembership, GymMembershipDto> mapper){
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public List<GymMembershipDto> getAll() {
        List<GymMembership> gymMemberships = dao.getAll();
        return mapper.mapToDto(gymMemberships);
    }
}