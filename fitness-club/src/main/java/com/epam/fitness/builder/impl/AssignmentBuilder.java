package com.epam.fitness.builder.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * <p>Builds an instance of the {@link Assignment} class.</p>
 *
 * @see Builder
 * @see Assignment
 */
public class AssignmentBuilder implements Builder<Assignment> {

    private static final String ID_COLUMN = "id";
    private static final String ORDER_ID_COLUMN = "order_id";
    private static final String EXERCISE_ID_COLUMN = "exercise_id";
    private static final String WORKOUT_DATE_COLUMN = "workout_date";
    private static final String AMOUNT_OF_SETS_COLUMN = "amount_of_sets";
    private static final String AMOUNT_OF_REPS_COLUMN = "amount_of_reps";
    private static final String EXERCISE_NAME_COLUMN = "name";
    private static final String STATUS_COLUMN = "status";

    /**
     * <p>Builds an instance of the {@link Assignment} class from
     * the supplied {@link ResultSet}.</p>
     *
     * @param resultSet a result set of parameters
     * @return a built assignment
     */
    @Override
    public Assignment build(ResultSet resultSet) throws SQLException {
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