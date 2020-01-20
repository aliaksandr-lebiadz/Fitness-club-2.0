package com.epam.fitness.mapper;

import com.epam.fitness.entity.assignment.Exercise;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class ExerciseMapperTest {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    private ExerciseMapper mapper = new ExerciseMapper();

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
        Exercise actual = mapper.mapRow(resultSet, ArgumentMatchers.anyInt());

        //then
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

}