package com.epam.fitness.mapper;

import com.epam.fitness.entity.assignment.Exercise;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ExerciseMapper implements RowMapper<Exercise> {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    @Override
    public Exercise mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        return new Exercise(id, name);
    }
}
