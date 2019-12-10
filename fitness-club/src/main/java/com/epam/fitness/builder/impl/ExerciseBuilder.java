package com.epam.fitness.builder.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.entity.assignment.Exercise;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>Builds an instance of the {@link Exercise} class.</p>
 *
 * @see Builder
 * @see Exercise
 */
public class ExerciseBuilder implements Builder<Exercise> {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    /**
     * <p>Builds an instance of the {@link Exercise} class from
     * the supplied {@link ResultSet}.</p>
     *
     * @param resultSet a result set of parameters
     * @return a built exercise
     */
    @Override
    public Exercise build(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        return new Exercise(id, name);
    }
}