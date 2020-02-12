package com.epam.fitness.mapper;

import com.epam.fitness.entity.assignment.Exercise;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.fitness.mapper.ExerciseMapper.ID_COLUMN;
import static com.epam.fitness.mapper.ExerciseMapper.NAME_COLUMN;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;

public class ExerciseMapperTest {

    private static final int ID = 10;
    private static final String NAME = "squat";
    private static final int ROW_INDEX = 5;

    private ExerciseMapper mapper = new ExerciseMapper();
    private ResultSet resultSet = mock(ResultSet.class);

    @Test
    public void testBuildShouldReturnBuiltExerciseWhenResultSetWithValidColumnsSupplied() throws SQLException {
        //given
        when(resultSet.getInt(ID_COLUMN)).thenReturn(ID);
        when(resultSet.getString(NAME_COLUMN)).thenReturn(NAME);
        Exercise expected = new Exercise(ID, NAME);

        //when
        Exercise actual = mapper.mapRow(resultSet, ROW_INDEX);

        //then
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verify(resultSet, times(1)).getString(NAME_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

    @Test(expected = SQLException.class)
    public void testBuildShouldThrowExceptionWhenResultSetWithInvalidColumnsSupplied() throws SQLException {
        //given
        when(resultSet.getInt(ID_COLUMN)).thenThrow(SQLException.class);

        //when
        mapper.mapRow(resultSet, ROW_INDEX);

        //then
        verify(resultSet, times(1)).getInt(ID_COLUMN);
        verifyNoMoreInteractions(resultSet);
    }

}