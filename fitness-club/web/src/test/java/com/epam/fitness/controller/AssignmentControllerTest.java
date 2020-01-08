package com.epam.fitness.controller;

import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.service.api.ExerciseService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class AssignmentControllerTest extends AbstractControllerTest{

    private static final String ASSIGNMENTS_PAGE_REQUEST = "/assignment/list";
    private static final String SET_STATUS_REQUEST = "/assignment/setStatus";
    private static final String ASSIGNMENT_ID_PARAMETER = "assignment_id";
    private static final String ASSIGNMENT_ACTION_PARAMETER = "assignment_action";
    private static final String REFERER_HEADER = "referer";
    private static final String CURRENT_PAGE = "http://localhost:8080/fitness/order/list";
    private static final String ERROR_PAGE_URL = "/error";
    private static final String ASSIGNMENTS_PAGE_VIEW_NAME = "assignments";

    private static final int ORDER_ID = 5;
    private static final int ASSIGNMENT_ID = 10;
    private static final NutritionType EXPECTED_NUTRITION_TYPE = NutritionType.LOW_CALORIE;
    private static final AssignmentStatus STATUS = AssignmentStatus.ACCEPTED;
    private static final List<Assignment> EXPECTED_ASSIGNMENTS = Arrays.asList(
            new Assignment(5, new Exercise("push-ups"), 5, 6, Date.valueOf("2019-12-11")),
            new Assignment(1, new Exercise("squat"), 1, 10, Date.valueOf("2020-11-08")),
            new Assignment(4, new Exercise("sit-ups"), 3, 8, Date.valueOf("2020-10-03")));
    private static final List<Exercise> EXPECTED_EXERCISES = Arrays.asList(
            new Exercise(1, "push-ups"),
            new Exercise(2, "squat"),
            new Exercise(3, "sit-ups"),
            new Exercise(4, "pull-ups"));

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private ExerciseService exerciseService;

    @Before
    public void createMocks() throws ServiceException {
        when(assignmentService.getAllByOrderId(ORDER_ID)).thenReturn(EXPECTED_ASSIGNMENTS);
        when(assignmentService.getNutritionTypeByOrderId(ORDER_ID)).thenReturn(EXPECTED_NUTRITION_TYPE);
        when(exerciseService.getAll()).thenReturn(EXPECTED_EXERCISES);
        doNothing().when(assignmentService).changeStatusById(ASSIGNMENT_ID, STATUS);
    }

    @Test
    public void testGetAssignmentsPage() throws Exception{
        final String assignmentsAttribute = "assignmentList";
        final String nutritionTypeAttribute = "nutrition_type";
        final String exercisesAttribute = "exerciseList";
        final String orderIdParameter = "order_id";
        mockMvc.perform(get(ASSIGNMENTS_PAGE_REQUEST)
                .param(orderIdParameter, String.valueOf(ORDER_ID)))
                .andExpect(status().isOk())
                .andExpect(model().size(3))
                .andExpect(model().attributeExists(assignmentsAttribute, nutritionTypeAttribute, exercisesAttribute))
                .andExpect(model().attribute(assignmentsAttribute, EXPECTED_ASSIGNMENTS))
                .andExpect(model().attribute(nutritionTypeAttribute, EXPECTED_NUTRITION_TYPE))
                .andExpect(model().attribute(exercisesAttribute, EXPECTED_EXERCISES))
                .andExpect(view().name(ASSIGNMENTS_PAGE_VIEW_NAME));

        verify(assignmentService, times(1)).getAllByOrderId(ORDER_ID);
        verify(assignmentService, times(1)).getNutritionTypeByOrderId(ORDER_ID);
        verify(exerciseService, times(1)).getAll();
        verifyNoMoreInteractions(assignmentService, exerciseService);
    }

    @Test
    public void testSetStatusWhenValidActionSupplied() throws Exception{
        final String validAssignmentAction = "accept";
        mockMvc.perform(post(SET_STATUS_REQUEST)
                .param(ASSIGNMENT_ID_PARAMETER, String.valueOf(ASSIGNMENT_ID))
                .param(ASSIGNMENT_ACTION_PARAMETER, validAssignmentAction)
                .header(REFERER_HEADER, CURRENT_PAGE))
                .andExpect(redirectedUrl(CURRENT_PAGE));

        verify(assignmentService, times(1)).changeStatusById(ASSIGNMENT_ID, STATUS);
        verifyNoMoreInteractions(assignmentService);
    }

    @Test
    public void testSetStatusWhenInvalidActionSupplied() throws Exception{
        final String invalidAssignmentAction = "delete";
        mockMvc.perform(post(SET_STATUS_REQUEST)
                .param(ASSIGNMENT_ID_PARAMETER, String.valueOf(ASSIGNMENT_ID))
                .param(ASSIGNMENT_ACTION_PARAMETER, invalidAssignmentAction))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));
    }

}