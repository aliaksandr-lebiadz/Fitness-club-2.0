package com.epam.fitness.service.api;

import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AssignmentService {

    void create(Assignment assignment) throws ServiceException;
    List<Assignment> getAllByOrderId(int orderId) throws ServiceException;
    NutritionType getNutritionTypeByOrderId(int orderId) throws ServiceException;
    void changeStatusById(int id, AssignmentStatus status) throws ServiceException;
    void updateById(int id, Exercise exercise, int amountOfSets, int amountOfReps, Date workoutDate) throws ServiceException;
    Optional<Assignment> findById(int id) throws ServiceException;
}