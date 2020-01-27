package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.AbstractDtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.GymMembershipDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GymMembershipDtoMapper extends AbstractDtoMapper<GymMembership, GymMembershipDto> {

    @Autowired
    public GymMembershipDtoMapper(ModelMapper modelMapper, Dao<GymMembership> gymMembershipDao){
        super(modelMapper, gymMembershipDao, GymMembershipDto.class);
    }

    @Override
    protected void setMutableFields(GymMembershipDto source, GymMembership destination) {
        //no mutable fields for this entity
    }

}
