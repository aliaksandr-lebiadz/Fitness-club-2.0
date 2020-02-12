package com.epam.fitness.service.api;

import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.exception.ServiceException;

import java.util.List;

public interface AssignmentService {

    void updateById(int id, AssignmentDto assignment) throws ServiceException;
    void create(int orderId, AssignmentDto assignmentDto) throws ServiceException;
    List<AssignmentDto> getAllByOrderId(int orderId) throws ServiceException;

}