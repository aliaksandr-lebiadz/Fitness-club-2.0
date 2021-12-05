package com.epam.fitness.service.api;

import com.epam.fitness.entity.ExerciseDto;

import java.util.List;

public interface ExerciseService {

    List<ExerciseDto> getAll();

}