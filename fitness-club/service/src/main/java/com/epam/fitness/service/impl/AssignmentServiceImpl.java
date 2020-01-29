package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.service.api.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    public void updateById(int id, AssignmentDto assignmentDto) throws ServiceException{
        //TODO refactor
        Optional<Assignment> assignmentOptional = assignmentDao.findById(id);
        Assignment assignment = assignmentOptional
                .orElseThrow(() -> new ServiceException("Assignment with id " + id + " not found!"));
        LocalDate workoutDate = assignmentDto.getWorkoutDate();
        if (Objects.nonNull(workoutDate)) {
            assignment.setWorkoutDate(workoutDate);
        }
        Integer amountOfSets = assignmentDto.getAmountOfSets();
        if(Objects.nonNull(amountOfSets)){
            assignment.setAmountOfSets(amountOfSets);
        }
        Integer amountOfReps = assignmentDto.getAmountOfReps();
        if(Objects.nonNull(amountOfReps)){
            assignment.setAmountOfReps(amountOfReps);
        }
        ExerciseDto exerciseDto = assignmentDto.getExercise();
        if(Objects.nonNull(exerciseDto)){
            int exerciseId = exerciseDto.getId();
            Optional<Exercise> exerciseOptional = exerciseDao.findById(exerciseId);
            Exercise exercise = exerciseOptional
                    .orElseThrow(() -> new ServiceException("Exercise with id " + exerciseId + " not found!"));
            assignment.setExercise(exercise);
        }
        AssignmentStatus assignmentStatus = assignmentDto.getStatus();
        if(assignmentStatus == AssignmentStatus.NEW){
            assignment.setStatus(AssignmentStatus.CHANGED);
        } else{
            assignment.setStatus(assignmentStatus);
        }
        assignmentDao.save(assignment);
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
    public void create(int orderId, AssignmentDto assignmentDto) throws ServiceException{
        Optional<Order> orderOptional = orderDao.findById(orderId);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + orderId + " not found!"));
        Assignment assignment = assignmentDtoMapper.mapToEntity(assignmentDto);
        assignment.setOrder(order);
        int exerciseId = assignmentDto.getExercise().getId();
        Optional<Exercise> exerciseOptional = exerciseDao.findById(exerciseId);
        Exercise exercise = exerciseOptional
                .orElseThrow(() -> new ServiceException("Exercise with id " + exerciseId + " not found!"));
        assignment.setExercise(exercise);
        assignmentDao.save(assignment);
    }
}