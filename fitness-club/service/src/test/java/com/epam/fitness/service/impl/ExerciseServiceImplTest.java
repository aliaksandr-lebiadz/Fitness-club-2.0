package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceImplTest {

    private static final List<Exercise> EXERCISES = Arrays.asList(
            new Exercise(1, "squat"),
            new Exercise(2, "sit-ups"),
            new Exercise(3, "pull-ups"));
    private static final List<ExerciseDto> EXPECTED_EXERCISES_DTO = Arrays.asList(
            new ExerciseDto(1, "squat"),
            new ExerciseDto(2, "sit-ups"),
            new ExerciseDto(3, "pull-ups"));

    @Mock
    private Dao<Exercise> exerciseDao;
    @Mock
    private DtoMapper<Exercise, ExerciseDto> exerciseMapper;
    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Test
    public void testGetAll() throws ServiceException {
        //given
        when(exerciseDao.getAll()).thenReturn(EXERCISES);
        when(exerciseMapper.mapToDto(EXERCISES)).thenReturn(EXPECTED_EXERCISES_DTO);

        //when
        List<ExerciseDto> actual = exerciseService.getAll();

        //then
        assertThat(actual, is(equalTo(EXPECTED_EXERCISES_DTO)));

        verify(exerciseDao, times(1)).getAll();
        verify(exerciseMapper, times(1)).mapToDto(EXERCISES);
        verifyNoMoreInteractions(exerciseDao, exerciseMapper);
    }

}