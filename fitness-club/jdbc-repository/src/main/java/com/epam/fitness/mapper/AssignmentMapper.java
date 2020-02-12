package com.epam.fitness.mapper;

import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Component
public class AssignmentMapper implements RowMapper<Assignment> {

    /*package-private*/ static final String ID_COLUMN = "id";
    /*package-private*/ static final String ORDER_ID_COLUMN = "order_id";
    /*package-private*/ static final String EXERCISE_ID_COLUMN = "exercise_id";
    /*package-private*/ static final String WORKOUT_DATE_COLUMN = "workout_date";
    /*package-private*/ static final String AMOUNT_OF_SETS_COLUMN = "amount_of_sets";
    /*package-private*/ static final String AMOUNT_OF_REPS_COLUMN = "amount_of_reps";
    /*package-private*/ static final String EXERCISE_NAME_COLUMN = "name";
    /*package-private*/ static final String STATUS_COLUMN = "status";

    @Override
    public Assignment mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        int orderId = resultSet.getInt(ORDER_ID_COLUMN);
        String exerciseName = resultSet.getString(EXERCISE_NAME_COLUMN);
        int exerciseId = resultSet.getInt(EXERCISE_ID_COLUMN);
        Exercise exercise = new Exercise(exerciseId, exerciseName);
        int amountOfSets = resultSet.getInt(AMOUNT_OF_SETS_COLUMN);
        int amountOfReps = resultSet.getInt(AMOUNT_OF_REPS_COLUMN);
        Date workoutDate = resultSet.getDate(WORKOUT_DATE_COLUMN);
        String assignmentStatusValue = resultSet.getString(STATUS_COLUMN);
        AssignmentStatus status = AssignmentStatus.valueOf(assignmentStatusValue.toUpperCase());
        return new Assignment(id, orderId, exercise, amountOfSets, amountOfReps, workoutDate, status);
    }
}
