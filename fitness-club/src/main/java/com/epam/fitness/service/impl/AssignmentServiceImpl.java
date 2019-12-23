package com.epam.fitness.service.impl;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private AssignmentDao assignmentDao;
    private OrderDao orderDao;

    @Autowired
    public AssignmentServiceImpl(@Qualifier("postgreSqlOrderDao") OrderDao orderDao,
                                 @Qualifier("postgreSqlAssignmentDao") AssignmentDao assignmentDao){
        this.orderDao = orderDao;
        this.assignmentDao = assignmentDao;
    }

    @Override
    public void create(Assignment assignment) {
        assignmentDao.save(assignment);
    }

    @Override
    public List<Assignment> getAllByOrderId(int orderId) {
        return assignmentDao.getAllByOrderId(orderId);
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
    public Optional<Assignment> findById(int id) {
        return assignmentDao.findById(id);
    }
}