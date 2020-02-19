package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.exception.DtoMappingException;
import com.epam.fitness.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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

    @Before
    public void createMocks() throws DtoMappingException {
        MockitoAnnotations.initMocks(this);

        when(exerciseDao.getAll()).thenReturn(EXERCISES);
        when(exerciseMapper.mapToDto(EXERCISES)).thenReturn(EXPECTED_EXERCISES_DTO);
    }

    @Test
    public void getAll() throws ServiceException {
        //given

        //when
        List<ExerciseDto> actual = exerciseService.getAll();

        //then
        assertThat(actual, is(equalTo(EXPECTED_EXERCISES_DTO)));

        verify(exerciseDao, times(1)).getAll();
        verify(exerciseMapper, times(1)).mapToDto(EXERCISES);
    }

    @After
    public void verifyMocks() {
        verifyNoMoreInteractions(exerciseDao, exerciseMapper);
        reset(exerciseDao, exerciseMapper);
    }

}