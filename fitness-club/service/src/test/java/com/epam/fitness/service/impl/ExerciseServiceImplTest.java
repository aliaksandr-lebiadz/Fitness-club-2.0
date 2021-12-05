package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.assignment.Exercise;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceImplTest {

    private static final Exercise SQUAT_EXERCISE = new Exercise(1, "squat");
    private static final Exercise SIT_UPS_EXERCISE = new Exercise(2, "sit-ups");
    private static final Exercise PULL_UPS_EXERCISE = new Exercise(3, "pull-ups");
    private static final ExerciseDto SQUAT_EXERCISE_DTO = new ExerciseDto(1, "squat");
    private static final ExerciseDto SIT_UPS_EXERCISE_DTO = new ExerciseDto(2, "sit-ups");
    private static final ExerciseDto PULL_UPS_EXERCISE_DTO = new ExerciseDto(3, "pull-ups");

    private static final List<Exercise> EXERCISES =
            Arrays.asList(SQUAT_EXERCISE, SIT_UPS_EXERCISE, PULL_UPS_EXERCISE);
    private static final List<ExerciseDto> EXPECTED_EXERCISES_DTO =
            Arrays.asList(SQUAT_EXERCISE_DTO, SIT_UPS_EXERCISE_DTO, PULL_UPS_EXERCISE_DTO);

    @Mock
    private Dao<Exercise> exerciseDao;
    @Mock
    private DtoMapper<Exercise, ExerciseDto> exerciseDtoMapper;
    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Test
    public void getAll_withExistentExercises_listOfExercises(){
        //given
        when(exerciseDao.getAll()).thenReturn(EXERCISES);
        when(exerciseDtoMapper.mapToDto(SQUAT_EXERCISE)).thenReturn(SQUAT_EXERCISE_DTO);
        when(exerciseDtoMapper.mapToDto(SIT_UPS_EXERCISE)).thenReturn(SIT_UPS_EXERCISE_DTO);
        when(exerciseDtoMapper.mapToDto(PULL_UPS_EXERCISE)).thenReturn(PULL_UPS_EXERCISE_DTO);

        //when
        List<ExerciseDto> actual = exerciseService.getAll();

        //then
        assertThat(actual, is(equalTo(EXPECTED_EXERCISES_DTO)));

        verify(exerciseDao, times(1)).getAll();
        verify(exerciseDtoMapper, times(1)).mapToDto(SQUAT_EXERCISE);
        verify(exerciseDtoMapper, times(1)).mapToDto(SIT_UPS_EXERCISE);
        verify(exerciseDtoMapper, times(1)).mapToDto(PULL_UPS_EXERCISE);
        verifyNoMoreInteractions(exerciseDao, exerciseDtoMapper);
    }

}