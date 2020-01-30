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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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
    public void updateById(int id, AssignmentDto assignmentDto) throws ServiceException{

        Optional<Assignment> assignmentOptional = assignmentDao.findById(id);
        Assignment assignment = assignmentOptional
                .orElseThrow(() -> new ServiceException("Assignment with id " + id + " not found!"));
        LocalDate workoutDate = assignmentDto.getWorkoutDate();
        Integer amountOfSets = assignmentDto.getAmountOfSets();
        Integer amountOfReps = assignmentDto.getAmountOfReps();
        ExerciseDto exerciseDto = assignmentDto.getExercise();
        updateFieldsWhenNonNull(assignment, workoutDate, amountOfSets, amountOfReps, exerciseDto);
        AssignmentStatus status = assignmentDto.getStatus();
        updateStatus(assignment, status);

        assignmentDao.save(assignment);
    }

    @Override
    public void create(int orderId, AssignmentDto assignmentDto) throws ServiceException{
        Optional<Order> orderOptional = orderDao.findById(orderId);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + orderId + " not found!"));
        Assignment assignment = assignmentMapper.mapToEntity(assignmentDto);
        assignment.setOrder(order);
        int exerciseId = assignmentDto.getExercise().getId();
        Optional<Exercise> exerciseOptional = exerciseDao.findById(exerciseId);
        Exercise exercise = exerciseOptional
                .orElseThrow(() -> new ServiceException("Exercise with id " + exerciseId + " not found!"));
        assignment.setExercise(exercise);
        assignmentDao.save(assignment);
    }

    private void updateFieldsWhenNonNull(Assignment assignment,
                                         LocalDate workoutDate, Integer amountOfSets,
                                         Integer amountOfReps, ExerciseDto exerciseDto) throws ServiceException{
        if (Objects.nonNull(workoutDate)) {
            assignment.setWorkoutDate(workoutDate);
        }
        if(Objects.nonNull(amountOfSets)){
            assignment.setAmountOfSets(amountOfSets);
        }
        if(Objects.nonNull(amountOfReps)){
            assignment.setAmountOfReps(amountOfReps);
        }
        if(Objects.nonNull(exerciseDto)){
            Exercise exercise = mapToEntity(exerciseDto);
            assignment.setExercise(exercise);
        }
    }

    private Exercise mapToEntity(ExerciseDto exerciseDto) throws ServiceException{
        int exerciseId = exerciseDto.getId();
        Optional<Exercise> exerciseOptional = exerciseDao.findById(exerciseId);
        return exerciseOptional
                .orElseThrow(() -> new ServiceException("Exercise with id " + exerciseId + " not found!"));
    }

    private void updateStatus(Assignment assignment, AssignmentStatus status){
        if(isNew(status)){
            assignment.setStatus(AssignmentStatus.CHANGED);
        } else{
            assignment.setStatus(status);
        }
    }

    private boolean isNew(AssignmentStatus status){
        return status == AssignmentStatus.NEW;
    }
}