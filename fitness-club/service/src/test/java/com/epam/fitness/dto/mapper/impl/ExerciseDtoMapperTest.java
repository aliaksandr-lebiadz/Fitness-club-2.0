package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.exception.DtoMappingException;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ExerciseDtoMapperTest {

    private static final Exercise FIRST_EXERCISE
            = new Exercise(1, "pull-ups");
    private static final Exercise SECOND_EXERCISE
            = new Exercise(2, "squat");
    private static final Exercise THIRD_EXERCISE
            = new Exercise(3, "sit-ups");

    private static final ExerciseDto FIRST_EXERCISE_DTO
            = new ExerciseDto(1, "pull-ups");
    private static final ExerciseDto SECOND_EXERCISE_DTO
            = new ExerciseDto(2, "squat");
    private static final ExerciseDto THIRD_EXERCISE_DTO
            = new ExerciseDto(3, "sit-ups");

    private static final List<Exercise> EXERCISES = Arrays.asList(
            FIRST_EXERCISE,
            SECOND_EXERCISE,
            THIRD_EXERCISE
    );
    private static final List<ExerciseDto> EXERCISES_DTO  = Arrays.asList(
            FIRST_EXERCISE_DTO,
            SECOND_EXERCISE_DTO,
            THIRD_EXERCISE_DTO
    );
    private static final List<Exercise> NULL_EXERCISES = Arrays.asList(
            null,
            null,
            null
    );

    private ModelMapper modelMapper = new ModelMapper();
    private DtoMapper<Exercise, ExerciseDto> mapper = new ExerciseDtoMapper(modelMapper);

    @Test
    public void mapToDto() throws DtoMappingException {
        //given

        //when
        ExerciseDto actual = mapper.mapToDto(SECOND_EXERCISE);

        //then
        assertEquals(SECOND_EXERCISE_DTO, actual);
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoWithDtoMappingException() throws DtoMappingException {
        //given
        final Exercise exercise = null;

        //when
        mapper.mapToDto(exercise);

        //then
    }

    @Test
    public void mapToEntity() throws DtoMappingException {
        //given

        //when
        Exercise actual = mapper.mapToEntity(THIRD_EXERCISE_DTO);

        //then
        assertEquals(THIRD_EXERCISE.getId(), actual.getId());
        assertEquals(THIRD_EXERCISE.getName(), actual.getName());
    }

    @Test(expected = DtoMappingException.class)
    public void mapToEntityWithDtoMappingException() throws DtoMappingException {
        //given
        final ExerciseDto exerciseDto = null;

        //when
        mapper.mapToEntity(exerciseDto);

        //then
    }

    @Test
    public void mapToDtoList() throws DtoMappingException {
        //given

        //when
        List<ExerciseDto> actual = mapper.mapToDto(EXERCISES);

        //then
        assertThat(actual, is(equalTo(EXERCISES_DTO)));
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoListWithDtoMappingException() throws DtoMappingException {
        //given

        //when
        mapper.mapToDto(NULL_EXERCISES);

        //then
    }

}