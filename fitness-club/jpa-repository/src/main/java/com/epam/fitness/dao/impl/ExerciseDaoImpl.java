package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.entity.assignment.Exercise;
import org.springframework.stereotype.Repository;

@Repository
public class ExerciseDaoImpl extends AbstractDao<Exercise> {

    public ExerciseDaoImpl(){
        super(Exercise.class);
    }
}