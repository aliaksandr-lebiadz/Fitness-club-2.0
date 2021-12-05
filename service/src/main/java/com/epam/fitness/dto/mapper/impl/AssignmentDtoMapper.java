package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.dto.mapper.AbstractDtoMapper;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.exception.EntityMappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssignmentDtoMapper extends AbstractDtoMapper<Assignment, AssignmentDto> {

    private DtoMapper<Exercise, ExerciseDto> exerciseDtoMapper;

    @Autowired
    public AssignmentDtoMapper(ModelMapper modelMapper, AssignmentDao assignmentDao,
                               DtoMapper<Exercise, ExerciseDto> exerciseDtoMapper){
        super(modelMapper, assignmentDao, AssignmentDto.class);
        this.exerciseDtoMapper = exerciseDtoMapper;
    }

    @Override
    protected void setMutableFields(AssignmentDto source, Assignment destination)
            throws EntityMappingException{
        destination.setAmountOfReps(source.getAmountOfReps());
        destination.setAmountOfSets(source.getAmountOfSets());
        ExerciseDto exerciseDto = source.getExercise();
        Exercise exercise = exerciseDtoMapper.mapToEntity(exerciseDto);
        destination.setExercise(exercise);
        destination.setWorkoutDate(source.getWorkoutDate());
    }

}
