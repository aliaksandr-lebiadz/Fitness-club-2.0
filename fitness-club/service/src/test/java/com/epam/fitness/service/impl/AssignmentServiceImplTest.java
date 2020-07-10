package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.DtoMappingException;
import com.epam.fitness.exception.EntityAlreadyExistsException;
import com.epam.fitness.exception.EntityNotFoundException;
import com.epam.fitness.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.criteria.Fetch;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssignmentServiceImplTest {

    private static final int EXISTENT_ASSIGNMENT_ID = 5;
    private static final int NONEXISTENT_ASSIGNMENT_ID = 13;
    private static final int EXISTENT_ORDER_ID = 3;
    private static final int NONEXISTENT_ORDER_ID = 33;
    private static final LocalDateTime ORDER_BEGIN_DATE = LocalDateTime.parse("2020-11-29T18:22:11");
    private static final LocalDateTime ORDER_END_DATE = LocalDateTime.parse("2021-03-11T13:11:09");
    private static final Order ORDER = Order
            .createBuilder()
            .setId(5)
            .setBeginDate(ORDER_BEGIN_DATE)
            .setEndDate(ORDER_END_DATE)
            .setPrice(BigDecimal.valueOf(15.2))
            .build();
    private static final int EXISTENT_EXERCISE_ID = 17;
    private static final Exercise EXERCISE = new Exercise(EXISTENT_EXERCISE_ID);
    private static final Assignment ASSIGNMENT =  new Assignment(EXISTENT_ASSIGNMENT_ID, LocalDate.parse("2020-11-11"), 5,
            2, EXERCISE, AssignmentStatus.NEW);
    private static final AssignmentDto ASSIGNMENT_DTO =
            new AssignmentDto(EXISTENT_ASSIGNMENT_ID, EXISTENT_EXERCISE_ID, 5, 2, LocalDate.parse("2020-11-11"));

    private static final int NONEXISTENT_EXERCISE_ID = 13;
    private static final AssignmentDto ASSIGNMENT_DTO_WITH_NONEXISTENT_EXERCISE_ID =
            new AssignmentDto(1, NONEXISTENT_EXERCISE_ID, 5, 2);
    private static final List<Assignment> ASSIGNMENTS = Arrays.asList(
            new Assignment(5, LocalDate.parse("2020-11-11"), 5, 2,
                    new Exercise(5), AssignmentStatus.NEW),
            new Assignment(11, LocalDate.parse("2020-06-12"), 1, 10,
                    new Exercise(3), AssignmentStatus.NEW)
    );
    private static final List<AssignmentDto> ASSIGNMENTS_DTO = Arrays.asList(
            new AssignmentDto(5, 5, 5, 2, LocalDate.parse("2020-11-11")),
            new AssignmentDto(11, 3, 1, 10, LocalDate.parse("2020-06-12"))
    );

    private static final AssignmentDto ASSIGNMENT_DTO_TO_CREATE =
            new AssignmentDto(NONEXISTENT_ASSIGNMENT_ID, EXISTENT_EXERCISE_ID, 2, 1, LocalDate.parse("2021-11-12"));

    @Mock
    private AssignmentDao assignmentDao;
    @Mock
    private OrderDao orderDao;
    @Mock
    private Dao<Exercise> exerciseDao;
    @Mock
    private DtoMapper<Assignment, AssignmentDto> assignmentMapper;
    @InjectMocks
    private AssignmentServiceImpl service;

    @Before
    public void createMocks() throws DtoMappingException {
        MockitoAnnotations.initMocks(this);

        when(orderDao.findById(EXISTENT_ORDER_ID)).thenReturn(Optional.of(ORDER));
        when(orderDao.findById(NONEXISTENT_ORDER_ID)).thenReturn(Optional.empty());

        when(exerciseDao.findById(NONEXISTENT_EXERCISE_ID)).thenReturn(Optional.empty());
        when(exerciseDao.findById(EXISTENT_EXERCISE_ID)).thenReturn(Optional.of(EXERCISE));

        when(assignmentDao.getAllByOrder(ORDER)).thenReturn(ASSIGNMENTS);
        when(assignmentDao.findById(EXISTENT_ASSIGNMENT_ID)).thenReturn(Optional.of(ASSIGNMENT));
        when(assignmentDao.findById(NONEXISTENT_ASSIGNMENT_ID)).thenReturn(Optional.empty());
        when(assignmentDao.save(ASSIGNMENT)).thenReturn(ASSIGNMENT);

        when(assignmentMapper.mapToDto(ASSIGNMENTS)).thenReturn(ASSIGNMENTS_DTO);
        when(assignmentMapper.mapToEntity(ASSIGNMENT_DTO_WITH_NONEXISTENT_EXERCISE_ID)).thenReturn(ASSIGNMENT);
        when(assignmentMapper.mapToEntity(ASSIGNMENT_DTO)).thenReturn(ASSIGNMENT);
        when(assignmentMapper.mapToDto(ASSIGNMENT)).thenReturn(ASSIGNMENT_DTO);
        when(assignmentMapper.mapToEntity(ASSIGNMENT_DTO_TO_CREATE)).thenReturn(ASSIGNMENT);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getAllByOrderIdWhenNonexistentOrderIdSupplied() throws ServiceException{
        //given

        //when/then
        try{
            service.getAllByOrderId(NONEXISTENT_ORDER_ID);
        } finally {
            verify(orderDao).findById(NONEXISTENT_ORDER_ID);
        }
    }

    @Test
    public void getAllByOrderId() throws ServiceException{
        //given

        //when
        List<AssignmentDto> actual = service.getAllByOrderId(EXISTENT_ORDER_ID);

        //then
        assertThat(actual, is(equalTo(ASSIGNMENTS_DTO)));

        verify(orderDao).findById(EXISTENT_ORDER_ID);
        verify(assignmentDao).getAllByOrder(ORDER);
        verify(assignmentMapper).mapToDto(ASSIGNMENTS);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateByIdWhenNonexistentIdSupplied() throws ServiceException{
        //given

        //when/then
        try{
            service.updateById(NONEXISTENT_ASSIGNMENT_ID, ASSIGNMENT_DTO_WITH_NONEXISTENT_EXERCISE_ID);
        } finally {
            verify(assignmentDao).findById(NONEXISTENT_ASSIGNMENT_ID);
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateByIdWhenNonexistentExerciseIdSupplied() throws ServiceException{
        //given

        //when/then
        try{
            service.updateById(EXISTENT_ASSIGNMENT_ID, ASSIGNMENT_DTO_WITH_NONEXISTENT_EXERCISE_ID);
        } finally {
            verify(assignmentDao).findById(EXISTENT_ASSIGNMENT_ID);
            verify(assignmentMapper).mapToEntity(ASSIGNMENT_DTO_WITH_NONEXISTENT_EXERCISE_ID);
            verify(exerciseDao).findById(NONEXISTENT_EXERCISE_ID);
        }
    }

    @Test
    public void updateById() throws ServiceException{
        //given

        //when
        AssignmentDto actual = service.updateById(EXISTENT_ASSIGNMENT_ID, ASSIGNMENT_DTO);

        //then
        assertEquals(ASSIGNMENT_DTO, actual);

        verify(assignmentDao).findById(EXISTENT_ASSIGNMENT_ID);
        verify(assignmentMapper).mapToEntity(ASSIGNMENT_DTO);
        verify(exerciseDao).findById(EXISTENT_EXERCISE_ID);
        verify(assignmentDao).save(ASSIGNMENT);
        verify(assignmentMapper).mapToDto(ASSIGNMENT);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createWhenExistentAssignmentIdSupplied() throws ServiceException{
        //given

        //when/then
        try{
            service.create(EXISTENT_ORDER_ID, ASSIGNMENT_DTO);
        } finally {
            verify(assignmentDao).findById(EXISTENT_ASSIGNMENT_ID);
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void createWhenNonexistentOrderIdSupplied() throws ServiceException{
        //given

        //when/then
        try{
            service.create(NONEXISTENT_ORDER_ID, ASSIGNMENT_DTO_TO_CREATE);
        } finally {
            verify(assignmentDao).findById(NONEXISTENT_ASSIGNMENT_ID);
            verify(assignmentMapper).mapToEntity(ASSIGNMENT_DTO_TO_CREATE);
            verify(orderDao).findById(NONEXISTENT_ORDER_ID);
        }
    }

    @Test
    public void create() throws ServiceException{
        //given

        //when
        AssignmentDto actual = service.create(EXISTENT_ORDER_ID, ASSIGNMENT_DTO_TO_CREATE);

        //then
        assertEquals(ASSIGNMENT_DTO, actual);

        verify(assignmentDao).findById(NONEXISTENT_ASSIGNMENT_ID);
        verify(assignmentMapper).mapToEntity(ASSIGNMENT_DTO_TO_CREATE);
        verify(orderDao).findById(EXISTENT_ORDER_ID);
        verify(exerciseDao).findById(EXISTENT_EXERCISE_ID);
        verify(assignmentDao).save(ASSIGNMENT);
        verify(assignmentMapper).mapToDto(ASSIGNMENT);
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(assignmentDao, orderDao, exerciseDao, assignmentMapper);
        reset(assignmentDao, orderDao, exerciseDao, assignmentMapper);
    }

}