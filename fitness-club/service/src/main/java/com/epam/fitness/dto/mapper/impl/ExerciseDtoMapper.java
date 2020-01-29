package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dto.mapper.AbstractDtoMapper;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.assignment.Exercise;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ExerciseDtoMapper extends AbstractDtoMapper<Exercise, ExerciseDto> {

    @Autowired
    public ExerciseDtoMapper(ModelMapper modelMapper){
        super(modelMapper, Exercise.class, ExerciseDto.class);
    }

}
