package com.epam.fitness.dao.impl.common;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.api.ExerciseDao;
import com.epam.fitness.dao.impl.AbstractDao;
import com.epam.fitness.entity.assignment.Exercise;

import java.sql.Connection;

/**
 * <p>An implementation of the exercise dao interface to provide
 * an access to the exercise entity in the MySql and PostgreSql database.</p>
 *
 * @see Exercise
 */
public class ExerciseDaoImpl extends AbstractDao<Exercise> implements ExerciseDao {

    private static final String EXERCISE_TABLE = "exercise";

    public ExerciseDaoImpl(Connection connection, Builder<Exercise> builder) {
        super(connection, builder);
    }

    @Override
    protected String getTableName() {
        return EXERCISE_TABLE;
    }

    @Override
    protected String getSaveQuery() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object[] getFields(Exercise exercise) {
        throw new UnsupportedOperationException();
    }
}