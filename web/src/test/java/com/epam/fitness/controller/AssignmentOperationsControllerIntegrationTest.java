package com.epam.fitness.controller;

import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.Order;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.sql.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(DataProviderRunner.class)
public class AssignmentOperationsControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String ADD_ASSIGNMENT_REQUEST = "/assignmentOperations/add";
    private static final String CHANGE_ASSIGNMENT_REQUEST = "/assignmentOperations/change";
    private static final String REFERER_HEADER = "referer";
    private static final String CURRENT_PAGE = "http://localhost:8080/fitness/order/list";
    private static final int AMOUNT_OF_REPS = 5;
    private static final int AMOUNT_OF_SETS = 90;
    private static final String VALID_WORKOUT_DATE_STR = "2021-11-06";
    private static final Date VALID_WORKOUT_DATE = Date.valueOf(VALID_WORKOUT_DATE_STR);
    private static final String INVALID_WORKOUT_DATE_STR = "2019-10-12";
    private static final Date INVALID_WORKOUT_DATE = Date.valueOf(INVALID_WORKOUT_DATE_STR);
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
    private static final Order ORDER = Order.createBuilder()
            .setId(ORDER_ID)
            .build();
    private static final Exercise EXERCISE = new Exercise(EXERCISE_ID, "squat");
    private static final Assignment ASSIGNMENT = new Assignment(ORDER, EXERCISE, AMOUNT_OF_SETS, AMOUNT_OF_REPS, VALID_WORKOUT_DATE);

    @DataProvider
    public static Object[][] invalidAmountDataProvider() {
        return new Object[][] {
                { 0 }, // < 1
                { 101 }, // > 100
                { -5 }, // negative
        };
    }

    @Autowired
    private Dao<Exercise> exerciseDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AssignmentDao assignmentDao;

    @Before
    public void setUp(){
        when(exerciseDao.findById(EXERCISE_ID)).thenReturn(Optional.of(EXERCISE));
        when(orderDao.findById(ORDER_ID)).thenReturn(Optional.of(ORDER));
        when(assignmentDao.findById(ASSIGNMENT_ID)).thenReturn(Optional.of(ASSIGNMENT));
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void add_withClientUserRole_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(ADD_ASSIGNMENT_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    @UseDataProvider("invalidAmountDataProvider")
    public void add_withInvalidAmountOfSets_redirectOnErrorPage(int amountOfSets) throws Exception {
        //given

        //when
        mockMvc.perform(post(ADD_ASSIGNMENT_REQUEST)
                .param(AMOUNT_OF_SETS_PARAMETER, String.valueOf(amountOfSets))
                .param(AMOUNT_OF_REPS_PARAMETER, String.valueOf(AMOUNT_OF_REPS))
                .param(WORKOUT_DATE_PARAMETER, VALID_WORKOUT_DATE_STR)
                .param(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID))
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    @UseDataProvider("invalidAmountDataProvider")
    public void add_withInvalidAmountOfReps_redirectOnErrorPage(int amountOfReps) throws Exception {
        //given

        //when
        mockMvc.perform(post(ADD_ASSIGNMENT_REQUEST)
                .param(AMOUNT_OF_SETS_PARAMETER, String.valueOf(AMOUNT_OF_SETS))
                .param(AMOUNT_OF_REPS_PARAMETER, String.valueOf(amountOfReps))
                .param(WORKOUT_DATE_PARAMETER, VALID_WORKOUT_DATE_STR)
                .param(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID))
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void add_withInvalidWorkoutDate_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(ADD_ASSIGNMENT_REQUEST)
                .param(AMOUNT_OF_SETS_PARAMETER, String.valueOf(AMOUNT_OF_SETS))
                .param(AMOUNT_OF_REPS_PARAMETER, String.valueOf(AMOUNT_OF_REPS))
                .param(WORKOUT_DATE_PARAMETER, INVALID_WORKOUT_DATE_STR)
                .param(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID))
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void add_withValidParams_assignmentSuccessfullyCreatedAndRedirectOnCurrentPage() throws Exception {
        //given
        when(assignmentDao.save(any(Assignment.class))).thenReturn(ASSIGNMENT);

        //when
        mockMvc.perform(post(ADD_ASSIGNMENT_REQUEST)
                .header(REFERER_HEADER, CURRENT_PAGE)
                .param(AMOUNT_OF_SETS_PARAMETER, String.valueOf(AMOUNT_OF_SETS))
                .param(AMOUNT_OF_REPS_PARAMETER, String.valueOf(AMOUNT_OF_REPS))
                .param(WORKOUT_DATE_PARAMETER, VALID_WORKOUT_DATE_STR)
                .param(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID))
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID)))
                .andExpect(redirectedUrl(CURRENT_PAGE));

        //then
        unwrapAndVerify(orderDao, times(1)).findById(ORDER_ID);
        unwrapAndVerify(exerciseDao, times(1)).findById(EXERCISE_ID);
        unwrapAndVerify(assignmentDao, times(1)).save(any(Assignment.class));
    }

    @Test
    @WithAnonymousUser
    public void change_withAnonymousUser_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(CHANGE_ASSIGNMENT_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    @UseDataProvider("invalidAmountDataProvider")
    public void change_withInvalidAmountOfSets_redirectOnErrorPage(int amountOfSets) throws Exception {
        //given

        //when
        mockMvc.perform(post(CHANGE_ASSIGNMENT_REQUEST)
            .param(AMOUNT_OF_SETS_PARAMETER, String.valueOf(amountOfSets))
            .param(AMOUNT_OF_REPS_PARAMETER, String.valueOf(AMOUNT_OF_REPS))
            .param(WORKOUT_DATE_PARAMETER, VALID_WORKOUT_DATE_STR)
            .param(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID))
            .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    @UseDataProvider("invalidAmountDataProvider")
    public void change_withInvalidAmountOfReps_redirectOnErrorPage(int amountOfReps) throws Exception {
        //given

        //when
        mockMvc.perform(post(CHANGE_ASSIGNMENT_REQUEST)
                .param(AMOUNT_OF_SETS_PARAMETER, String.valueOf(AMOUNT_OF_SETS))
                .param(AMOUNT_OF_REPS_PARAMETER, String.valueOf(amountOfReps))
                .param(WORKOUT_DATE_PARAMETER, VALID_WORKOUT_DATE_STR)
                .param(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID))
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void change_withInvalidWorkoutDate_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(CHANGE_ASSIGNMENT_REQUEST)
                .param(AMOUNT_OF_SETS_PARAMETER, String.valueOf(AMOUNT_OF_SETS))
                .param(AMOUNT_OF_REPS_PARAMETER, String.valueOf(AMOUNT_OF_REPS))
                .param(WORKOUT_DATE_PARAMETER, INVALID_WORKOUT_DATE_STR)
                .param(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID))
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void change_withValidParams_assignmentSuccessfullyCreatedAndRedirectOnCurrentPage() throws Exception {
        //given
        when(assignmentDao.save(any(Assignment.class))).thenReturn(ASSIGNMENT);

        //when
        mockMvc.perform(post(CHANGE_ASSIGNMENT_REQUEST)
                .header(REFERER_HEADER, CURRENT_PAGE)
                .param(AMOUNT_OF_SETS_PARAMETER, String.valueOf(AMOUNT_OF_SETS))
                .param(AMOUNT_OF_REPS_PARAMETER, String.valueOf(AMOUNT_OF_REPS))
                .param(WORKOUT_DATE_PARAMETER, VALID_WORKOUT_DATE_STR)
                .param(EXERCISE_SELECT_PARAMETER, String.valueOf(EXERCISE_ID))
                .param(ASSIGNMENT_ID_PARAMETER, String.valueOf(ASSIGNMENT_ID)))
                .andExpect(redirectedUrl(CURRENT_PAGE));

        //then
        unwrapAndVerify(assignmentDao, times(1)).findById(ASSIGNMENT_ID);
        unwrapAndVerify(exerciseDao, times(1)).findById(EXERCISE_ID);
        unwrapAndVerify(assignmentDao, times(1)).save(any(Assignment.class));
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(orderDao, exerciseDao, assignmentDao);
        reset(orderDao, exerciseDao, assignmentDao);
    }

}