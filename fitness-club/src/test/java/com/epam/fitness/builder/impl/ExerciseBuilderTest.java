package com.epam.fitness.builder.impl;

import com.epam.fitness.entity.assignment.Exercise;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class ExerciseBuilderTest {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    private ExerciseBuilder builder = new ExerciseBuilder();

    @Test
    public void testBuildShouldReturnBuiltExerciseEntity() throws SQLException {
        //given
        final int id = 10;
        final String name = "squat";
        Exercise expected = new Exercise(id, name);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(ID_COLUMN)).thenReturn(id);
        when(resultSet.getString(NAME_COLUMN)).thenReturn(name);

        //when
        Exercise actual = builder.build(resultSet);

        //then
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

}