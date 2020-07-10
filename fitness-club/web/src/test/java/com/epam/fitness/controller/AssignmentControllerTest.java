package com.epam.fitness.controller;

import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.exception.EntityNotFoundException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssignmentControllerTest extends AbstractControllerTest{

    private static final String ASSIGNMENTS_BY_ID_URL = "/assignments/{id}";
    private static final int EXISTENT_ASSIGNMENT_ID = 13;
    private static final int NONEXISTENT_ASSIGNMENT_ID = 1;
    private static final AssignmentDto ASSIGNMENT_DTO = new AssignmentDto(EXISTENT_ASSIGNMENT_ID, 3, 2, 1);

    @Mock
    private AssignmentService assignmentService;
    @InjectMocks
    private AssignmentController controller;

    @Before
    public void setUp(){
        configureMockMvc(controller);
    }

    @Before
    public void createMocks() throws ServiceException{
        when(assignmentService.updateById(EXISTENT_ASSIGNMENT_ID, ASSIGNMENT_DTO)).thenReturn(ASSIGNMENT_DTO);
        when(assignmentService.updateById(NONEXISTENT_ASSIGNMENT_ID, ASSIGNMENT_DTO)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    public void updateAssignmentByIdWhenExistentAssignmentIdSupplied() throws Exception{
        //given
        String requestBody = mapToJson(ASSIGNMENT_DTO);

        //when
        String actualJson = mockMvc
                .perform(put(ASSIGNMENTS_BY_ID_URL, EXISTENT_ASSIGNMENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AssignmentDto actual = mapFromJson(actualJson, AssignmentDto.class);

        //then
        assertEquals(ASSIGNMENT_DTO, actual);

        verify(assignmentService, times(1)).updateById(EXISTENT_ASSIGNMENT_ID, ASSIGNMENT_DTO);
    }

    @Test
    public void updateAssignmentByIdWhenNonexistentAssignmentIdSupplied() throws Exception{
        //given
        String requestBody = mapToJson(ASSIGNMENT_DTO);

        //when
        mockMvc
                .perform(put(ASSIGNMENTS_BY_ID_URL, NONEXISTENT_ASSIGNMENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());

        //then
        verify(assignmentService, times(1)).updateById(NONEXISTENT_ASSIGNMENT_ID, ASSIGNMENT_DTO);
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(assignmentService);
        reset(assignmentService);
    }

}