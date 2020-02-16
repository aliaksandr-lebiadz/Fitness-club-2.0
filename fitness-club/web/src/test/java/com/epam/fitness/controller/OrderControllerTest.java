package com.epam.fitness.controller;

import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.exception.EntityAlreadyExistsException;
import com.epam.fitness.exception.EntityNotFoundException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.service.api.OrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends AbstractControllerTest {

    private static final String ORDERS_BY_ID_URL = "/orders/{id}";
    private static final String ASSIGNMENTS_BY_ORDER_ID_URL = "/orders/{id}/assignments";
    private static final int EXISTENT_ORDER_ID = 5;
    private static final int NONEXISTENT_ORDER_ID = 13;
    private static final OrderDto ORDER_DTO = new OrderDto(EXISTENT_ORDER_ID, "feedbackkkkk", BigDecimal.valueOf(15.2));
    private static final List<AssignmentDto> EXPECTED_ASSIGNMENTS_DTO = Arrays.asList(
            new AssignmentDto(1, AssignmentStatus.CANCELED),
            new AssignmentDto(15, AssignmentStatus.NEW),
            new AssignmentDto(32, AssignmentStatus.CHANGED)
    );
    private static final AssignmentDto ASSIGNMENT_DTO = new AssignmentDto(5, 3, 2, 1);
    private static final AssignmentDto EXISTENT_ASSIGNMENT_DTO = new AssignmentDto(3, 2, 7, 11);

    @Mock
    private OrderService orderService;
    @Mock
    private AssignmentService assignmentService;
    @InjectMocks
    private OrderController controller;

    @Before
    public void setUp() {
        configureMockMvc(controller);
    }

    @Before
    public void createMocks() throws ServiceException{
        when(orderService.getById(EXISTENT_ORDER_ID)).thenReturn(ORDER_DTO);
        when(orderService.getById(NONEXISTENT_ORDER_ID)).thenThrow(EntityNotFoundException.class);

        when(orderService.updateById(EXISTENT_ORDER_ID, ORDER_DTO)).thenReturn(ORDER_DTO);
        when(orderService.updateById(NONEXISTENT_ORDER_ID, ORDER_DTO)).thenThrow(EntityNotFoundException.class);

        when(assignmentService.getAllByOrderId(EXISTENT_ORDER_ID)).thenReturn(EXPECTED_ASSIGNMENTS_DTO);
        when(assignmentService.getAllByOrderId(NONEXISTENT_ORDER_ID)).thenThrow(EntityNotFoundException.class);

        when(assignmentService.create(EXISTENT_ORDER_ID, ASSIGNMENT_DTO)).thenReturn(ASSIGNMENT_DTO);
        when(assignmentService.create(NONEXISTENT_ORDER_ID, ASSIGNMENT_DTO)).thenThrow(EntityNotFoundException.class);
        when(assignmentService.create(EXISTENT_ORDER_ID, EXISTENT_ASSIGNMENT_DTO)).thenThrow(EntityAlreadyExistsException.class);
    }

    @Test
    public void getOrderByIdWhenExistentOrderIdSupplied() throws Exception{
        //given

        //when
        String actualJson = mockMvc
                .perform(get(ORDERS_BY_ID_URL, EXISTENT_ORDER_ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderDto actual = mapFromJson(actualJson, OrderDto.class);

        //then
        assertEquals(ORDER_DTO, actual);

        verify(orderService, times(1)).getById(EXISTENT_ORDER_ID);
    }

    @Test
    public void getOrderByIdWhenNonexistentOrderIdSupplied() throws Exception{
        //given

        //when
        mockMvc
                .perform(get(ORDERS_BY_ID_URL, NONEXISTENT_ORDER_ID))
                .andExpect(status().isNotFound());

        //then
        verify(orderService, times(1)).getById(NONEXISTENT_ORDER_ID);
    }

    @Test
    public void updateOrderByIdWhenExistentOrderIdSupplied() throws Exception{
        //given
        String requestBody = mapToJson(ORDER_DTO);

        //when
        String actualJson = mockMvc
                .perform(put(ORDERS_BY_ID_URL, EXISTENT_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderDto actual = mapFromJson(actualJson, OrderDto.class);

        //then
        assertEquals(ORDER_DTO, actual);

        verify(orderService, times(1)).updateById(EXISTENT_ORDER_ID, ORDER_DTO);

        //then
    }

    @Test
    public void updateOrderByIdWhenNonexistentOrderIdSupplied() throws Exception{
        //given
        String requestBody = mapToJson(ORDER_DTO);

        //when
        mockMvc
                .perform(put(ORDERS_BY_ID_URL, NONEXISTENT_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());

        //then
        verify(orderService, times(1)).updateById(NONEXISTENT_ORDER_ID, ORDER_DTO);
    }

    @Test
    public void getAssignmentsByOrderIdWhenExistentOrderIdSupplied() throws Exception{
        //given

        //when
        String actualJson = mockMvc
                .perform(get(ASSIGNMENTS_BY_ORDER_ID_URL, EXISTENT_ORDER_ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AssignmentDto[] assignmentsDto = mapFromJson(actualJson, AssignmentDto[].class);
        List<AssignmentDto> actual = Arrays.asList(assignmentsDto);

        //then
        assertThat(actual, is(equalTo(EXPECTED_ASSIGNMENTS_DTO)));

        verify(assignmentService, times(1)).getAllByOrderId(EXISTENT_ORDER_ID);
    }

    @Test
    public void getAssignmentsByOrderIdWhenNonexistentOrderIdSupplied() throws Exception{
        //given

        //when
        mockMvc
                .perform(get(ASSIGNMENTS_BY_ORDER_ID_URL, NONEXISTENT_ORDER_ID))
                .andExpect(status().isNotFound());

        //then
        verify(assignmentService, times(1)).getAllByOrderId(NONEXISTENT_ORDER_ID);
    }

    @Test
    public void createAssignmentWhenExistentOrderIdSupplied() throws Exception{
        //given
        String requestBody = mapToJson(ASSIGNMENT_DTO);

        //when
        String actualJson = mockMvc
                .perform(post(ASSIGNMENTS_BY_ORDER_ID_URL, EXISTENT_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AssignmentDto actual = mapFromJson(actualJson, AssignmentDto.class);

        //then
        assertEquals(ASSIGNMENT_DTO, actual);

        verify(assignmentService, times(1)).create(EXISTENT_ORDER_ID, ASSIGNMENT_DTO);
    }

    @Test
    public void createAssignmentWhenNonexistentOrderIdSupplied() throws Exception{
        //given
        String requestBody = mapToJson(ASSIGNMENT_DTO);

        //when
        mockMvc
                .perform(post(ASSIGNMENTS_BY_ORDER_ID_URL, NONEXISTENT_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());

        //then
        verify(assignmentService, times(1)).create(NONEXISTENT_ORDER_ID, ASSIGNMENT_DTO);
    }

    @Test
    public void createAssignmentWhenSuppliedAssignmentAlreadyExists() throws Exception{
        //given
        String requestBody = mapToJson(EXISTENT_ASSIGNMENT_DTO);

        //when
        mockMvc
                .perform(post(ASSIGNMENTS_BY_ORDER_ID_URL, EXISTENT_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict());

        //then
        verify(assignmentService, times(1)).create(EXISTENT_ORDER_ID, EXISTENT_ASSIGNMENT_DTO);
    }

    @After
    public void verifyMocks() {
        verifyNoMoreInteractions(orderService, assignmentService);
        reset(orderService, assignmentService);
    }

}