package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.service.api.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private Dao<Exercise> dao;
    private DtoMapper<Exercise, ExerciseDto> mapper;

    @Autowired
    public ExerciseServiceImpl(Dao<Exercise> dao, DtoMapper<Exercise, ExerciseDto> mapper){
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public List<ExerciseDto> getAll() {
        List<Exercise> exercises = dao.getAll();
        return exercises.stream()
                .map(user -> mapper.mapToDto(user))
                .collect(Collectors.toList());
    }
}