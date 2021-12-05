package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.AbstractDtoMapper;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.assignment.Exercise;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ExerciseDtoMapper extends AbstractDtoMapper<Exercise, ExerciseDto> {

    @Autowired
    public ExerciseDtoMapper(ModelMapper modelMapper, Dao<Exercise> exerciseDao){
        super(modelMapper, exerciseDao, ExerciseDto.class);
    }

    @Override
    protected void setMutableFields(ExerciseDto source, Exercise destination) {
        //no mutable fields for this entity
    }

}
