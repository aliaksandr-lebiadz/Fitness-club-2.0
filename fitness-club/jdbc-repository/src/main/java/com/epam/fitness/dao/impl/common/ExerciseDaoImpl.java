package com.epam.fitness.dao.impl.common;

import com.epam.fitness.dao.api.ExerciseDao;
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
public class ExerciseDaoImpl extends AbstractDao<Exercise> implements ExerciseDao {

    private static final String EXERCISE_TABLE = "exercise";

    @Autowired
    public ExerciseDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<Exercise> rowMapper){
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