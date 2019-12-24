package com.epam.fitness.dao.impl.mysql;

import com.epam.fitness.dao.impl.AbstractAssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>An implementation of the assignment dao interface to provide
 * an access to the assignment entity in the MySql database.</p>
 *
 * @see Assignment
 */
@Repository
public class MySqlAssignmentDao extends AbstractAssignmentDao {

    private static final String SAVE_ASSIGNMENT_QUERY = "INSERT INTO assignment" +
                    "(id, order_id, exercise_id, amount_of_sets, amount_of_reps, workout_date, status) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "id = VALUES(id)," +
                    "order_id = VALUES(order_id)," +
                    "exercise_id = VALUES(exercise_id)," +
                    "amount_of_sets = VALUES(amount_of_sets)," +
                    "amount_of_reps = VALUES(amount_of_reps)," +
                    "workout_date = VALUES(workout_date)," +
                    "status = VALUES(status)";

    @Autowired
    public MySqlAssignmentDao(JdbcTemplate jdbcTemplate, RowMapper<Assignment> rowMapper){
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
        return new Object[]{
                assignment.getId(),
                assignment.getOrderId(),
                exercise.getId(),
                assignment.getAmountOfSets(),
                assignment.getAmountOfReps(),
                assignment.getWorkoutDate(),
                status.toString()
        };
    }
}