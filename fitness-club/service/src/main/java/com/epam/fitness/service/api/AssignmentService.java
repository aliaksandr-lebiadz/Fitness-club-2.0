package com.epam.fitness.service.api;

import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface AssignmentService {

    NutritionType getNutritionTypeByOrderId(int orderId) throws ServiceException;
    void changeStatusById(int id, AssignmentStatus status) throws ServiceException;
    void updateById(int id, Exercise exercise, int amountOfSets, int amountOfReps, Date workoutDate) throws ServiceException;

    @Transactional
    void create(int orderId, int exerciseId, int amountOfSets, int amountOfReps, Date workoutDate) throws ServiceException;

    @Transactional
    List<Assignment> getAllByOrderId(int orderId) throws ServiceException;
}