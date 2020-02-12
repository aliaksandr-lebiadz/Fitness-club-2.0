
package com.epam.fitness.service.api;

import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.exception.ServiceException;

import java.util.List;

public interface OrderService {

    void create(int clientId, GymMembershipDto gymMembershipDto) throws ServiceException;
    void updateById(int id, OrderDto order) throws ServiceException;
    List<OrderDto> getOrdersOfTrainerClient(int clientId, int trainerId) throws ServiceException;
    List<OrderDto> getOrdersByClientId(int clientId) throws ServiceException;
    OrderDto getById(int id) throws ServiceException;

}