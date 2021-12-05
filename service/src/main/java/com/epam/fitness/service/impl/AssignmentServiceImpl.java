package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.EntityMappingException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.service.api.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private AssignmentDao assignmentDao;
    private OrderDao orderDao;
    private Dao<Exercise> exerciseDao;
    private DtoMapper<Assignment, AssignmentDto> assignmentDtoMapper;

    @Autowired
    public AssignmentServiceImpl(OrderDao orderDao,
                                 AssignmentDao assignmentDao,
                                 Dao<Exercise> exerciseDao,
                                 DtoMapper<Assignment, AssignmentDto> assignmentDtoMapper){
        this.orderDao = orderDao;
        this.assignmentDao = assignmentDao;
        this.exerciseDao = exerciseDao;
        this.assignmentDtoMapper = assignmentDtoMapper;
    }

    @Override
    public List<AssignmentDto> getAllByOrderId(int orderId) throws ServiceException{
        Optional<Order> orderOptional = orderDao.findById(orderId);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + orderId + " not found!"));
        List<Assignment> assignments = assignmentDao.getAllByOrder(order);
        return assignments.stream()
                .map(assignment -> assignmentDtoMapper.mapToDto(assignment))
                .collect(Collectors.toList());
    }

    @Override
    public void update(AssignmentDto assignmentDto) throws ServiceException{
        try{
            Assignment assignment = assignmentDtoMapper.mapToEntity(assignmentDto);
            assignment.setStatus(AssignmentStatus.CHANGED);
            assignmentDao.save(assignment);
        } catch (EntityMappingException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void updateStatusById(int id, AssignmentStatus status) throws ServiceException{
        Optional<Assignment> assignmentOptional = assignmentDao.findById(id);
        Assignment assignment = assignmentOptional
                .orElseThrow(() -> new ServiceException("Assignment with id " + id + " not found!"));
        assignment.setStatus(status);
        assignmentDao.save(assignment);
    }

    @Override
    public void create(int orderId, int exerciseId, AssignmentDto assignmentDto)
            throws ServiceException {
        Optional<Order> orderOptional = orderDao.findById(orderId);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + orderId + " not found!"));
        Optional<Exercise> exerciseOptional = exerciseDao.findById(exerciseId);
        Exercise exercise = exerciseOptional
                .orElseThrow(() -> new ServiceException("Exercise with id " + exerciseId + " not found!"));
        Assignment assignment = createAssignment(order, exercise, assignmentDto);
        assignmentDao.save(assignment);
    }

    private Assignment createAssignment(Order order, Exercise exercise, AssignmentDto assignmentDto){
        int amountOfSets = assignmentDto.getAmountOfSets();
        int amountOfReps = assignmentDto.getAmountOfReps();
        Date workoutDate = assignmentDto.getWorkoutDate();
        return new Assignment(order, exercise, amountOfSets, amountOfReps, workoutDate);
    }
}