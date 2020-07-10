package com.epam.fitness.controller;

import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.ExerciseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExerciseControllerTest extends AbstractControllerTest{

    private static final String EXERCISES_URL = "/exercises";
    private static final List<ExerciseDto> EXERCISES_DTO = Arrays.asList(
            new ExerciseDto(1, "push-ups"),
            new ExerciseDto(2, "pull-ups"),
            new ExerciseDto(3, "squat"),
            new ExerciseDto(4, "sit-ups")
    );

    @Mock
    private ExerciseService exerciseService;
    @InjectMocks
    private ExerciseController controller;

    @Before
    public void setUp() {
        configureMockMvc(controller);
    }

    @Before
    public void createMocks() throws ServiceException {
        when(exerciseService.getAll()).thenReturn(EXERCISES_DTO);
    }

    @Test
    public void getExercises() throws Exception {
        //given

        //when
        String actualJson = mockMvc
                .perform(get(EXERCISES_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ExerciseDto[] exercisesDto = mapFromJson(actualJson, ExerciseDto[].class);
        List<ExerciseDto> actual = Arrays.asList(exercisesDto);

        //then
        assertThat(actual, is(equalTo(EXERCISES_DTO)));

        verify(exerciseService, times(1)).getAll();
        verifyNoMoreInteractions(exerciseService);
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(exerciseService);
        reset(exerciseService);
    }

}