package com.epam.fitness.service.api;

import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.exception.ServiceException;

import javax.transaction.Transactional;
import java.util.List;

public interface AssignmentService {

    void updateStatusById(int id, AssignmentStatus status) throws ServiceException;
    void updateById(int id, AssignmentDto assignment) throws ServiceException;

    @Transactional
    void create(int orderId, AssignmentDto assignmentDto) throws ServiceException;

    @Transactional
    List<AssignmentDto> getAllByOrderId(int orderId) throws ServiceException;
}