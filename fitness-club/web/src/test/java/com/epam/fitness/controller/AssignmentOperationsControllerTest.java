package com.epam.fitness.controller;

import com.epam.fitness.config.SpringWebMvcConfig;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.validator.api.AssignmentValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringWebMvcConfig.class)
public class AssignmentOperationsControllerTest {

    private static final String ADD_ASSIGNMENT_REQUEST = "/assignmentOperations/add";
    private static final String CHANGE_ASSIGNMENT_REQUEST = "/assignmentOperations/change";
    private static final String REFERER_HEADER = "referer";
    private static final String CURRENT_PAGE = "http://localhost:8080/fitness/order/list";
    private static final int VALID_AMOUNT_OF_REPS = 5;
    private static final int VALID_AMOUNT_OF_SETS = 90;
    private static final String VALID_WORKOUT_DATE_STR = "2020-11-06";
    private static final String INVALID_WORKOUT_DATE_STR = "2019-12-30";
    private static final Date VALID_WORKOUT_DATE = Date.valueOf(VALID_WORKOUT_DATE_STR);
    private static final Date INVALID_WORKOUT_DATE = Date.valueOf(INVALID_WORKOUT_DATE_STR);
    private static final int INVALID_AMOUNT_OF_REPS = 102;
    private static final int INVALID_AMOUNT_OF_SETS = -5;
    private static final int EXERCISE_ID = 5;
    private static final int ORDER_ID = 3;
    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final String EXERCISE_SELECT_PARAMETER = "exercise_select";
    private static final String WORKOUT_DATE_PARAMETER = "date";
    private static final String AMOUNT_OF_SETS_PARAMETER = "amount_of_sets";
    private static final String AMOUNT_OF_REPS_PARAMETER = "amount_of_reps";
    private static final String ASSIGNMENT_ID_PARAMETER = "assignment_id";
    private static final int ASSIGNMENT_ID = 11;
    private static final String ERROR_PAGE_URL = "/error";
    private static final Order ORDER;

    static{
        ORDER = Order.createBuilder()
                .setId(ORDER_ID)
                .build();
    }

    private static final Assignment ASSIGNMENT = new Assignment(ORDER, new Exercise(EXERCISE_ID), VALID_AMOUNT_OF_SETS,
            VALID_AMOUNT_OF_REPS, VALID_WORKOUT_DATE);

    private MockMvc mockMvc;

    @Mock
    private AssignmentService service;
    @Mock
    private AssignmentValidator validator;
    @InjectMocks
    private AssignmentOperationsController assignmentOperationsController;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(assignmentOperationsController)
                .setControllerAdvice(new ControllerAdviceImpl())
                .build();
    }

    @Before
    public void createMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        when(validator.isAmountOfRepsValid(VALID_AMOUNT_OF_REPS)).thenReturn(true);
        when(validator.isAmountOfSetsValid(VALID_AMOUNT_OF_SETS)).thenReturn(true);
        when(validator.isWorkoutDateValid(VALID_WORKOUT_DATE)).thenReturn(true);
        when(validator.isAmountOfRepsValid(INVALID_AMOUNT_OF_REPS)).thenReturn(false);
        when(validator.isAmountOfSetsValid(INVALID_AMOUNT_OF_SETS)).thenReturn(false);
        when(validator.isWorkoutDateValid(INVALID_WORKOUT_DATE)).thenReturn(false);
        doNothing().when(service).create(ORDER_ID, EXERCISE_ID, VALID_AMOUNT_OF_SETS, VALID_AMOUNT_OF_REPS, VALID_WORKOUT_DATE);
        doNothing().when(service).updateById(ASSIGNMENT_ID,
                new Exercise(EXERCISE_ID), VALID_AMOUNT_OF_SETS, VALID_AMOUNT_OF_REPS, VALID_WORKOUT_DATE);
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void testAddWhenValidParametersSuppliedAndUserIsAuthorizedAsTrainer() throws Exception{
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(AMOUNT_OF_SETS_PARAMETER, String.valueOf(VALID_AMOUNT_OF_SETS));
        params.add(AMOUNT_OF_REPS_PARAMETER, String.valueOf(VALID_AMOUNT_OF_REPS));
        params.add(WORKOUT_DATE_PARAMETER, VALID_WORKOUT_DATE_STR);
        params.add(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID));
        params.add(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID));

        //when
        mockMvc.perform(post(ADD_ASSIGNMENT_REQUEST)
                .params(params)
                .header(REFERER_HEADER, CURRENT_PAGE))
                .andExpect(redirectedUrl(CURRENT_PAGE));

        //then
        verify(validator, times(1)).isAmountOfRepsValid(VALID_AMOUNT_OF_REPS);
        verify(validator, times(1)).isAmountOfSetsValid(VALID_AMOUNT_OF_SETS);
        verify(validator, times(1)).isWorkoutDateValid(VALID_WORKOUT_DATE);
        verify(service, times(1))
                .create(ORDER_ID, EXERCISE_ID, VALID_AMOUNT_OF_SETS, VALID_AMOUNT_OF_REPS, VALID_WORKOUT_DATE);
    }

    @Test()
    @WithMockUser(authorities = "TRAINER")
    public void testAddWhenInvalidParametersSuppliedAndUserIsAuthorizedAsTrainer() throws Exception{
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(AMOUNT_OF_SETS_PARAMETER, String.valueOf(INVALID_AMOUNT_OF_SETS));
        params.add(AMOUNT_OF_REPS_PARAMETER, String.valueOf(INVALID_AMOUNT_OF_REPS));
        params.add(WORKOUT_DATE_PARAMETER, INVALID_WORKOUT_DATE_STR);
        params.add(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID));
        params.add(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID));

        //when
        mockMvc.perform(post(ADD_ASSIGNMENT_REQUEST)
                .params(params))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
        verify(validator, times(1)).isAmountOfSetsValid(INVALID_AMOUNT_OF_SETS);
        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = { "CLIENT", "ADMIN" } )
    public void testAddShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsTrainer() throws Exception{
        //given

        //when
        mockMvc.perform(post(ADD_ASSIGNMENT_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = { "CLIENT", "TRAINER" } )
    public void testChangeWhenValidParametersSuppliedAndUserIsAuthorizedAsTrainerOrClient() throws Exception{
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(AMOUNT_OF_SETS_PARAMETER, String.valueOf(VALID_AMOUNT_OF_SETS));
        params.add(AMOUNT_OF_REPS_PARAMETER, String.valueOf(VALID_AMOUNT_OF_REPS));
        params.add(WORKOUT_DATE_PARAMETER, VALID_WORKOUT_DATE_STR);
        params.add(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID));
        params.add(ASSIGNMENT_ID_PARAMETER, String.valueOf(ASSIGNMENT_ID));

        //when
        mockMvc.perform(post(CHANGE_ASSIGNMENT_REQUEST)
                .params(params)
                .header(REFERER_HEADER, CURRENT_PAGE))
                .andExpect(redirectedUrl(CURRENT_PAGE));

        //then
        verify(validator, times(1)).isAmountOfRepsValid(VALID_AMOUNT_OF_REPS);
        verify(validator, times(1)).isAmountOfSetsValid(VALID_AMOUNT_OF_SETS);
        verify(validator, times(1)).isWorkoutDateValid(VALID_WORKOUT_DATE);
        verify(service, times(1)).updateById(ASSIGNMENT_ID,
                new Exercise(EXERCISE_ID), VALID_AMOUNT_OF_SETS, VALID_AMOUNT_OF_REPS, VALID_WORKOUT_DATE);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testChangeShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsTrainerOrClient() throws Exception{
        //given

        //when
        mockMvc.perform(post(CHANGE_ASSIGNMENT_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(validator, service);
        reset(validator, service);
    }

}