package com.epam.fitness.dao.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.api.ExerciseDao;
import com.epam.fitness.entity.assignment.Exercise;

import java.sql.Connection;

/**
 * <p>An implementation of the exercise dao interface to provide
 * an access to the exercise entity in the MySql database.</p>
 *
 * @see Exercise
 */
public class ExerciseDaoImpl extends AbstractDao<Exercise> implements ExerciseDao {

    private static final String EXERCISE_TABLE = "exercise";

    public ExerciseDaoImpl(Connection connection, Builder<Exercise> builder) {
        super(connection, builder);
    }

    @Override
    public void save(Exercise entity) {}

    @Override
    protected String getTableName() {
        return EXERCISE_TABLE;
    }
}