package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.EntityAlreadyExistsException;
import com.epam.fitness.exception.EntityNotFoundException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.service.api.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private AssignmentDao assignmentDao;
    private OrderDao orderDao;
    private Dao<Exercise> exerciseDao;
    private DtoMapper<Assignment, AssignmentDto> assignmentMapper;

    @Autowired
    public AssignmentServiceImpl(OrderDao orderDao,
                                 AssignmentDao assignmentDao,
                                 Dao<Exercise> exerciseDao,
                                 DtoMapper<Assignment, AssignmentDto> assignmentMapper){
        this.orderDao = orderDao;
        this.assignmentDao = assignmentDao;
        this.exerciseDao = exerciseDao;
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    public List<AssignmentDto> getAllByOrderId(int orderId) throws ServiceException{
        Optional<Order> orderOptional = orderDao.findById(orderId);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + orderId + " not found!"));
        List<Assignment> assignments = assignmentDao.getAllByOrder(order);
        return assignmentMapper.mapToDto(assignments);
    }

    @Override
    public AssignmentDto updateById(int id, AssignmentDto assignmentDto) throws ServiceException{
        Assignment assignment = assignmentMapper.mapToEntity(assignmentDto);
        mapExerciseFromDtoToEntity(assignmentDto, assignment);
        assignment.setId(id);
        Assignment savedAssignment = assignmentDao.save(assignment);
        return assignmentMapper.mapToDto(savedAssignment);
    }

    @Override
    public AssignmentDto create(int orderId, AssignmentDto assignmentDto) throws ServiceException{
        Integer id = assignmentDto.getId();
        if(Objects.nonNull(id)){
            Optional<Assignment> assignmentOptional = assignmentDao.findById(id);
            if(assignmentOptional.isPresent()){
                throw new EntityAlreadyExistsException("Assignment with id " + id + " already exists!");
            }
        }
        Assignment assignment = assignmentMapper.mapToEntity(assignmentDto);
        Optional<Order> orderOptional = orderDao.findById(orderId);
        Order order = orderOptional
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " not found!"));
        assignment.setOrder(order);
        mapExerciseFromDtoToEntity(assignmentDto, assignment);
        Assignment savedAssignment = assignmentDao.save(assignment);
        return assignmentMapper.mapToDto(savedAssignment);
    }

    private void mapExerciseFromDtoToEntity(AssignmentDto assignmentDto, Assignment assignment)
            throws EntityNotFoundException {
        int exerciseId = assignmentDto.getExerciseId();
        Optional<Exercise> exerciseOptional = exerciseDao.findById(exerciseId);
        Exercise exercise = exerciseOptional
                .orElseThrow(() -> new EntityNotFoundException("Exercise with id " + exerciseId + " not found!"));
        assignment.setExercise(exercise);
    }

}