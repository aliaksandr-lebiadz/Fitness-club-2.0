package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.service.api.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private AssignmentDao assignmentDao;
    private OrderDao orderDao;
    private Dao<Exercise> exerciseDao;

    @Autowired
    public AssignmentServiceImpl(OrderDao orderDao,
                                 AssignmentDao assignmentDao,
                                 Dao<Exercise> exerciseDao){
        this.orderDao = orderDao;
        this.assignmentDao = assignmentDao;
        this.exerciseDao = exerciseDao;
    }

    @Override
    public List<Assignment> getAllByOrderId(int orderId) throws ServiceException{
        Optional<Order> orderOptional = orderDao.findById(orderId);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + orderId + " not found!"));
        return assignmentDao.getAllByOrder(order);
    }

    @Override
    public NutritionType getNutritionTypeByOrderId(int orderId) throws ServiceException {
        Optional<Order> orderOptional = orderDao.findById(orderId);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with the id " + orderId + " isn't found!"));
        return order.getNutritionType();
    }

    @Override
    public void changeStatusById(int id, AssignmentStatus status) throws ServiceException {
        Optional<Assignment> assignmentOptional = assignmentDao.findById(id);
        Assignment assignment = assignmentOptional
                .orElseThrow(() -> new ServiceException("Assignment with the id " + id + " isn't found!"));
        assignment.setStatus(status);
        assignmentDao.save(assignment);
    }

    @Override
    public void updateById(int id, Exercise exercise, int amountOfSets, int amountOfReps, Date workoutDate) throws ServiceException {
        Optional<Assignment> assignmentOptional = assignmentDao.findById(id);
        Assignment assignment = assignmentOptional
                .orElseThrow(() -> new ServiceException("Assignment with id " + id + " not found!"));
        assignment.setExercise(exercise);
        assignment.setAmountOfSets(amountOfSets);
        assignment.setAmountOfReps(amountOfReps);
        assignment.setWorkoutDate(workoutDate);
        assignment.setStatus(AssignmentStatus.CHANGED);
        assignmentDao.save(assignment);
    }

    @Override
    public void create(int orderId, int exerciseId, int amountOfSets, int amountOfReps, Date workoutDate)
            throws ServiceException {
        Optional<Order> orderOptional = orderDao.findById(orderId);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + orderId + " not found!"));
        Optional<Exercise> exerciseOptional = exerciseDao.findById(exerciseId);
        Exercise exercise = exerciseOptional
                .orElseThrow(() -> new ServiceException("Exercise with id " + exerciseId + " not found!"));
        Assignment assignment = new Assignment(order, exercise, amountOfSets, amountOfReps, workoutDate);
        assignmentDao.save(assignment);
    }
}