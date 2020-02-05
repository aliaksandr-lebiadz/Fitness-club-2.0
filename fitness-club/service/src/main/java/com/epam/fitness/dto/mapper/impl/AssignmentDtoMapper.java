package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dto.mapper.AbstractDtoMapper;
import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.assignment.Assignment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssignmentDtoMapper extends AbstractDtoMapper<Assignment, AssignmentDto> {

    @Autowired
    public AssignmentDtoMapper(ModelMapper modelMapper){
        super(modelMapper, Assignment.class, AssignmentDto.class);
    }

}
