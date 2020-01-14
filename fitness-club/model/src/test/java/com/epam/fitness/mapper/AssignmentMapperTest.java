package com.epam.fitness.mapper;

import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AssignmentMapperTest {

    private static final String ID_COLUMN = "id";
    private static final String ORDER_ID_COLUMN = "order_id";
    private static final String EXERCISE_ID_COLUMN = "exercise_id";
    private static final String WORKOUT_DATE_COLUMN = "workout_date";
    private static final String AMOUNT_OF_SETS_COLUMN = "amount_of_sets";
    private static final String AMOUNT_OF_REPS_COLUMN = "amount_of_reps";
    private static final String EXERCISE_NAME_COLUMN = "name";
    private static final String STATUS_COLUMN = "status";

    private AssignmentMapper mapper = new AssignmentMapper();

    @Test
    public void testBuildShouldReturnBuiltAssignmentEntity() throws SQLException {
        //given
        final int id = 5;
        final int orderId = 10;
        final int exerciseId = 3;
        final Date workoutDate = Date.valueOf("2019-12-11");
        final int amountOfReps = 10;
        final int amountOfSets = 11;
        final String exerciseName = "push-up";
        final String statusValue = "accepted";
        AssignmentStatus status = AssignmentStatus.valueOf(statusValue.toUpperCase());
        Assignment expected =
                new Assignment(id, orderId, new Exercise(exerciseId, exerciseName), amountOfSets, amountOfReps, workoutDate, status);

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt(ID_COLUMN)).thenReturn(id);
        Mockito.when(resultSet.getInt(ORDER_ID_COLUMN)).thenReturn(orderId);
        Mockito.when(resultSet.getInt(EXERCISE_ID_COLUMN)).thenReturn(exerciseId);
        Mockito.when(resultSet.getDate(WORKOUT_DATE_COLUMN)).thenReturn(workoutDate);
        Mockito.when(resultSet.getInt(AMOUNT_OF_SETS_COLUMN)).thenReturn(amountOfSets);
        Mockito.when(resultSet.getInt(AMOUNT_OF_REPS_COLUMN)).thenReturn(amountOfReps);
        Mockito.when(resultSet.getString(EXERCISE_NAME_COLUMN)).thenReturn(exerciseName);
        Mockito.when(resultSet.getString(STATUS_COLUMN)).thenReturn(statusValue);

        //when
        Assignment actual = mapper.mapRow(resultSet, ArgumentMatchers.anyInt());

        //then
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getOrderId(), actual.getOrderId());
        Assert.assertEquals(expected.getExercise().getId(), actual.getExercise().getId());
        Assert.assertEquals(expected.getExercise().getName(), actual.getExercise().getName());
        Assert.assertEquals(expected.getAmountOfSets(), actual.getAmountOfSets());
        Assert.assertEquals(expected.getAmountOfReps(), actual.getAmountOfReps());
        Assert.assertEquals(expected.getWorkoutDate(), actual.getWorkoutDate());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
    }

}