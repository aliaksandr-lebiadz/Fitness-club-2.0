package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.entity.assignment.Exercise;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExerciseDaoImpl extends AbstractDao<Exercise> {

    @Autowired
    public ExerciseDaoImpl(SessionFactory sessionFactory){
        super(sessionFactory, Exercise.class);
    }
}