package com.epam.fitness.service.impl;

import com.epam.fitness.dao.factory.DaoFactory;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.DaoException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.service.api.AssignmentService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AssignmentServiceImpl implements AssignmentService {

    private AssignmentDao assigmentDao;
    private OrderDao orderDao;

    public AssignmentServiceImpl(DaoFactory factory){
        this.assigmentDao = factory.createAssignmentDao();
        this.orderDao = factory.createOrderDao();
    }

    @Override
    public void create(Assignment assignment) throws ServiceException {
        try{
            assigmentDao.save(assignment);
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Assignment> getAllByOrderId(int orderId) throws ServiceException {
        try{
            return assigmentDao.getAllByOrderId(orderId);
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public NutritionType getNutritionTypeByOrderId(int orderId) throws ServiceException {
        try{
            Optional<Order> orderOptional = orderDao.findById(orderId);
            Order order = orderOptional
                    .orElseThrow(() -> new ServiceException("Order with the id " + orderId + " isn't found!"));
            return order.getNutritionType();
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void changeStatusById(int id, AssignmentStatus status) throws ServiceException {
        try{
            Optional<Assignment> assignmentOptional = assigmentDao.findById(id);
            Assignment assignment = assignmentOptional
                    .orElseThrow(() -> new ServiceException("Assignment with the id " + id + " isn't found!"));
            assignment.setStatus(status);
            assigmentDao.save(assignment);
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void updateById(int id, Exercise exercise, int amountOfSets, int amountOfReps, Date workoutDate)
            throws ServiceException {
        try{
            Optional<Assignment> assignmentOptional = assigmentDao.findById(id);
            if(assignmentOptional.isPresent()){
                Assignment assignment = assignmentOptional.get();
                assignment.setExercise(exercise);
                assignment.setAmountOfSets(amountOfSets);
                assignment.setAmountOfReps(amountOfReps);
                assignment.setWorkoutDate(workoutDate);
                assignment.setStatus(AssignmentStatus.CHANGED);
                assigmentDao.save(assignment);
            }
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<Assignment> findById(int id) throws ServiceException {
        try{
            return assigmentDao.findById(id);
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}