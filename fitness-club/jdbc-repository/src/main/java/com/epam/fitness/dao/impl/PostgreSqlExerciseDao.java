package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.entity.assignment.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>An implementation of the exercise dao interface to provide
 * an access to the exercise entity in the MySql and PostgreSql database.</p>
 *
 * @see Exercise
 */
@Repository
public class PostgreSqlExerciseDao extends AbstractDao<Exercise>{

    private static final String EXERCISE_TABLE = "exercise";

    @Autowired
    public PostgreSqlExerciseDao(JdbcTemplate jdbcTemplate, RowMapper<Exercise> rowMapper){
        super(jdbcTemplate, rowMapper);
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