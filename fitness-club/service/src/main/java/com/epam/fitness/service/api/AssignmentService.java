package com.epam.fitness.service.api;

import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.exception.ServiceException;

import javax.transaction.Transactional;
import java.util.List;

public interface AssignmentService {

    void updateStatusById(int id, AssignmentStatus status) throws ServiceException;
    void update(AssignmentDto assignment) throws ServiceException;

    @Transactional
    void create(int orderId, int exerciseId, AssignmentDto assignment) throws ServiceException;

    @Transactional
    List<AssignmentDto> getAllByOrderId(int orderId) throws ServiceException;
}