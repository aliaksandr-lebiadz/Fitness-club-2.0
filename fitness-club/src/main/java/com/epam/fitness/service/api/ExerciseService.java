package com.epam.fitness.service.api;

import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.entity.assignment.Exercise;

import java.util.List;

public interface ExerciseService {

    List<Exercise> getAll() throws ServiceException;

}