package com.epam.fitness.dao.impl.postgresql;

import com.epam.fitness.dao.impl.AbstractAssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class PostgreSqlAssignmentDao extends AbstractAssignmentDao {

    private static final String SAVE_ASSIGNMENT_QUERY = "INSERT INTO assignment" +
            "(id, order_id, exercise_id, amount_of_sets, amount_of_reps, workout_date, status) " +
            "VALUES(COALESCE(?, (SELECT MAX(id) FROM client_order as uid) + 1, 1), ?, ?, ?, ?, ?, ?::status) " +
            "ON CONFLICT(id) DO UPDATE SET " +
            "order_id = EXCLUDED.order_id," +
            "exercise_id = EXCLUDED.exercise_id," +
            "amount_of_sets = EXCLUDED.amount_of_sets," +
            "amount_of_reps = EXCLUDED.amount_of_reps," +
            "workout_date = EXCLUDED.workout_date," +
            "status = EXCLUDED.status::status";

    @Autowired
    public PostgreSqlAssignmentDao(JdbcTemplate jdbcTemplate, RowMapper<Assignment> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_ASSIGNMENT_QUERY;
    }

    @Override
    protected Object[] getFields(Assignment assignment) {
        Exercise exercise = assignment.getExercise();
        AssignmentStatus status = assignment.getStatus();
        String statusValue = status.toString();
        return new Object[]{
                assignment.getId(),
                assignment.getOrderId(),
                exercise.getId(),
                assignment.getAmountOfSets(),
                assignment.getAmountOfReps(),
                convertToSqlDate(assignment.getWorkoutDate()),
                statusValue.toLowerCase()
        };
    }

    private java.sql.Date convertToSqlDate(Date date){
        long time = date.getTime();
        return new java.sql.Date(time);
    }
}
