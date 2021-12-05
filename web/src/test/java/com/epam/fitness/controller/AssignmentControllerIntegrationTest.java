package com.epam.fitness.controller;

import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AssignmentControllerIntegrationTest extends AbstractIntegrationTest {

    private static final int EXISTENT_ORDER_ID = 5;
    private static final int NONEXISTENT_ORDER_ID = 12;
    private static final int EXISTENT_ASSIGNMENT_ID = 11;
    private static final int NONEXISTENT_ASSIGNMENT_ID = 3;
    private static final String INVALID_ACTION = "delete";
    private static final String VALID_ACTION = "cancel";
    private static final Order ORDER = Order.createBuilder()
            .setId(EXISTENT_ORDER_ID)
            .setNutritionType(NutritionType.HIGH_CALORIE)
            .setBeginDate(Date.valueOf("2020-05-12"))
            .setEndDate(Date.valueOf("2020-11-11"))
            .setPrice(BigDecimal.ONE)
            .setAssignments(Collections.emptyList())
            .build();
    private static final Exercise EXERCISE = new Exercise(2, "push-ups");
    private static final Assignment ASSIGNMENT = new Assignment(1, ORDER, EXERCISE, 3, 5, Date.valueOf("2020-11-11"), AssignmentStatus.ACCEPTED);
    private static final Assignment UPDATED_ASSIGNMENT = new Assignment(1, ORDER, EXERCISE, 3, 5, Date.valueOf("2020-11-11"), AssignmentStatus.CANCELED);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AssignmentDao assignmentDao;

    @Autowired
    private Dao<Exercise> exerciseDao;

    @Before
    public void setUp() {
        when(orderDao.findById(EXISTENT_ORDER_ID)).thenReturn(Optional.of(ORDER));
        when(orderDao.findById(NONEXISTENT_ORDER_ID)).thenReturn(Optional.empty());
        when(assignmentDao.getAllByOrder(ORDER)).thenReturn(Collections.singletonList(ASSIGNMENT));
        when(exerciseDao.getAll()).thenReturn(Collections.singletonList(EXERCISE));
        when(assignmentDao.findById(EXISTENT_ASSIGNMENT_ID)).thenReturn(Optional.of(ASSIGNMENT));
        when(assignmentDao.findById(NONEXISTENT_ASSIGNMENT_ID)).thenReturn(Optional.empty());
        when(assignmentDao.save(UPDATED_ASSIGNMENT)).thenReturn(UPDATED_ASSIGNMENT);
    }

    @Test
    @WithAnonymousUser
    public void getAssignmentsPage_withAnonymousUser_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get("/assignment/list"))
                .andExpect(redirectedUrl("/error"));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void getAssignmentsPage_withoutOrderId_redirectOnOrdersPage() throws Exception {
        //given

        //when
        mockMvc.perform(get("/assignment/list"))
                .andExpect(redirectedUrl("/order/list"));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void getAssignmentsPage_withNonexistentOrderId_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get("/assignment/list")
                .param("order_id", String.valueOf(NONEXISTENT_ORDER_ID)))
                .andExpect(redirectedUrl("/error"));

        //then
        unwrapAndVerify(orderDao, times(1)).findById(NONEXISTENT_ORDER_ID);
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void getAssignmentsPage_withExistentOrderId_assignmentsPageView() throws Exception {
        //given

        //when
        mockMvc.perform(get("/assignment/list")
                .param("order_id", String.valueOf(EXISTENT_ORDER_ID)))
                .andExpect(model().attribute("assignmentList", hasSize(1)))
                .andExpect(model().attribute("assignmentList", hasItem(allOf(
                        hasProperty("id", is(ASSIGNMENT.getId())),
                        hasProperty("status", is(ASSIGNMENT.getStatus())),
                        hasProperty("amountOfSets", is(ASSIGNMENT.getAmountOfSets())),
                        hasProperty("amountOfReps", is(ASSIGNMENT.getAmountOfReps()))
                ))))
                .andExpect(model().attribute("nutrition_type", is(ORDER.getNutritionType())))
                .andExpect(model().attribute("exerciseList", hasSize(1)))
                .andExpect(model().attribute("exerciseList", hasItem(allOf(
                        hasProperty("id", is(EXERCISE.getId())),
                        hasProperty("name", is(EXERCISE.getName()))
                ))))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments"));

        //then
        unwrapAndVerify(orderDao, times(2)).findById(EXISTENT_ORDER_ID);
        unwrapAndVerify(assignmentDao, times(1)).getAllByOrder(ORDER);
        unwrapAndVerify(exerciseDao, times(1)).getAll();
    }

    @Test
    @WithAnonymousUser
    public void setStatus_withAnonymousUser_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post("/assignment/setStatus")
                .param("assignment_id", String.valueOf(EXISTENT_ASSIGNMENT_ID))
                .param("assignment_action", VALID_ACTION))
                .andExpect(redirectedUrl("/error"));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void setStatus_withInvalidAction_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post("/assignment/setStatus")
                .param("assignment_id", String.valueOf(EXISTENT_ASSIGNMENT_ID))
                .param("assignment_action", INVALID_ACTION))
                .andExpect(redirectedUrl("/error"));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void setStatus_withNonexistentAssignmentId_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post("/assignment/setStatus")
                .param("assignment_id", String.valueOf(NONEXISTENT_ASSIGNMENT_ID))
                .param("assignment_action", VALID_ACTION))
                .andExpect(redirectedUrl("/error"));

        //then
        unwrapAndVerify(assignmentDao, times(1)).findById(NONEXISTENT_ASSIGNMENT_ID);
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void setStatus_withExistentAssignmentIdAndValidStatus_assignmentSuccessfullyUpdated() throws Exception {
        //given
        final String currentPage = "current-page";

        //when
        mockMvc.perform(post("/assignment/setStatus")
                .header("referer", currentPage)
                .param("assignment_id", String.valueOf(EXISTENT_ASSIGNMENT_ID))
                .param("assignment_action", VALID_ACTION))
                .andExpect(redirectedUrl(currentPage));

        //then
        unwrapAndVerify(assignmentDao, times(1)).findById(EXISTENT_ASSIGNMENT_ID);
        unwrapAndVerify(assignmentDao, times(1)).save(UPDATED_ASSIGNMENT);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(orderDao, assignmentDao, exerciseDao);
        reset(orderDao, assignmentDao, exerciseDao);
    }
}
