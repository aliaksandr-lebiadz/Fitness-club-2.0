package com.epam.fitness.service.impl;

import com.epam.fitness.dao.factory.DaoFactory;
import com.epam.fitness.exception.DaoException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.ExerciseDao;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.service.api.ExerciseService;

import java.util.List;

public class ExerciseServiceImpl implements ExerciseService {

    private ExerciseDao exerciseDao;

    public ExerciseServiceImpl(DaoFactory factory){
        this.exerciseDao = factory.createExerciseDao();
    }

    @Override
    public List<Exercise> getAll() throws ServiceException {
        try{
            return exerciseDao.getAll();
        } catch(DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}