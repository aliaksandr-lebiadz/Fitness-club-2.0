package com.epam.fitness.service.api;

import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.exception.ServiceException;

import java.util.List;

public interface ExerciseService {

    List<ExerciseDto> getAll() throws ServiceException;

}