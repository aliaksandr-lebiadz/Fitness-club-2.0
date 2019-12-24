package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.ExerciseDao;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.service.api.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private ExerciseDao exerciseDao;

    @Autowired
    public ExerciseServiceImpl(ExerciseDao exerciseDao){
        this.exerciseDao = exerciseDao;
    }

    @Override
    public List<Exercise> getAll() {
        return exerciseDao.getAll();
    }
}