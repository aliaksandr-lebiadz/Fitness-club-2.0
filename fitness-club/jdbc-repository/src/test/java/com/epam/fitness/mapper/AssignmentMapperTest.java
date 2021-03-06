package com.epam.fitness.mapper;

import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import static com.epam.fitness.mapper.AssignmentMapper.ID_COLUMN;
import static com.epam.fitness.mapper.AssignmentMapper.ORDER_ID_COLUMN;
import static com.epam.fitness.mapper.AssignmentMapper.EXERCISE_ID_COLUMN;
import static com.epam.fitness.mapper.AssignmentMapper.WORKOUT_DATE_COLUMN;
import static com.epam.fitness.mapper.AssignmentMapper.AMOUNT_OF_SETS_COLUMN;
import static com.epam.fitness.mapper.AssignmentMapper.AMOUNT_OF_REPS_COLUMN;
import static com.epam.fitness.mapper.AssignmentMapper.EXERCISE_NAME_COLUMN;
import static com.epam.fitness.mapper.AssignmentMapper.STATUS_COLUMN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;

public class AssignmentMapperTest {

    private static final int ID = 5;
    private static final int ORDER_ID = 10;
    private static final int EXERCISE_ID = 3;
    private static final Date WORKOUT_DATE = Date.valueOf("2019-12-11");
    private static final int AMOUNT_OF_REPS = 10;
    private static final int AMOUNT_OF_SETS = 11;
    private static final String EXERCISE_NAME = "push-up";
    private static final String ASSIGNMENT_STATUS_VALUE = "ACCEPTED";
    private static final int ROW_INDEX = 1;

    private AssignmentMapper mapper = new AssignmentMapper();
    private ResultSet resultSet = mock(ResultSet.class);

    @Test
    public void buildWhenValidResultSetSupplied() throws SQLException {
        //given
        when(resultSet.getInt(ID_COLUMN)).thenReturn(ID);
        when(resultSet.getInt(ORDER_ID_COLUMN)).thenReturn(ORDER_ID);
        when(resultSet.getInt(EXERCISE_ID_COLUMN)).thenReturn(EXERCISE_ID);
        when(resultSet.getDate(WORKOUT_DATE_COLUMN)).thenReturn(WORKOUT_DATE);
        when(resultSet.getInt(AMOUNT_OF_SETS_COLUMN)).thenReturn(AMOUNT_OF_SETS);
        when(resultSet.getInt(AMOUNT_OF_REPS_COLUMN)).thenReturn(AMOUNT_OF_REPS);
        when(resultSet.getString(EXERCISE_NAME_COLUMN)).thenReturn(EXERCISE_NAME);
        when(resultSet.getString(STATUS_COLUMN)).thenReturn(ASSIGNMENT_STATUS_VALUE);

        AssignmentStatus status =
                AssignmentStatus.valueOf(ASSIGNMENT_STATUS_VALUE);
        Exercise exercise = new Exercise(EXERCISE_ID, EXERCISE_NAME);
        Assignment expected =
                new Assignment(ID, ORDER_ID, exercise, AMOUNT_OF_SETS, AMOUNT_OF_REPS, WORKOUT_DATE, status);

        //when
        Assignment actual = mapper.mapRow(resultSet, ROW_INDEX);

        //then
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getOrderId(), actual.getOrderId());
        assertEquals(expected.getExercise().getId(), actual.getExercise().getId());
        assertEquals(expected.getExercise().getName(), actual.getExercise().getName());
        assertEquals(expected.getAmountOfSets(), actual.getAmountOfSets());
        assertEquals(expected.getAmountOfReps(), actual.getAmountOfReps());
        assertEquals(expected.getWorkoutDate(), actual.getWorkoutDate());
        assertEquals(expected.getStatus(), actual.getStatus());

        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getInt(ORDER_ID_COLUMN);
        verify(resultSet, times(1)).getInt(EXERCISE_ID_COLUMN);
        verify(resultSet, times(1)).getDate(WORKOUT_DATE_COLUMN);
        verify(resultSet, times(1)).getInt(AMOUNT_OF_SETS_COLUMN);
        verify(resultSet, times(1)).getInt(AMOUNT_OF_REPS_COLUMN);
        verify(resultSet, times(1)).getString(EXERCISE_NAME_COLUMN);
        verify(resultSet, times(1)).getString(STATUS_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

    @Test(expected = SQLException.class)
    public void buildWithSqlExceptionWhenInvalidResultSetSupplied() throws SQLException{
        //given
        when(resultSet.getInt(ORDER_ID_COLUMN)).thenThrow(SQLException.class);

        //when
        mapper.mapRow(resultSet, ROW_INDEX);

        //then
        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getInt(ORDER_ID_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

}