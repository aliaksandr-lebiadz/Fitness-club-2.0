package com.epam.fitness.dao.factory.impl;

import com.epam.fitness.builder.impl.ExerciseBuilder;
import com.epam.fitness.builder.impl.GymMembershipBuilder;
import com.epam.fitness.dao.api.ExerciseDao;
import com.epam.fitness.dao.api.GymMembershipDao;
import com.epam.fitness.dao.factory.DaoFactory;
import com.epam.fitness.dao.impl.common.ExerciseDaoImpl;
import com.epam.fitness.dao.impl.common.GymMembershipDaoImpl;

import java.sql.Connection;

public abstract class AbstractDaoFactory implements DaoFactory {

    private Connection connection;

    public AbstractDaoFactory(Connection connection) {
        this.connection = connection;
    }

    public ExerciseDao createExerciseDao() {
        return new ExerciseDaoImpl(connection, new ExerciseBuilder());
    }

    public GymMembershipDao createGymMembershipDao() {
        return new GymMembershipDaoImpl(connection, new GymMembershipBuilder());
    }

    /*package-private*/ Connection getConnection(){
        return connection;
    }

}
