
package com.epam.fitness.service.api;

import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderService {

    @Transactional
    void create(int clientId, GymMembershipDto gymMembershipDto) throws ServiceException;
    @Transactional
    void updateById(int id, OrderDto order) throws ServiceException;
    List<OrderDto> getOrdersOfTrainerClient(int clientId, UserDto trainer);
    @Transactional
    List<OrderDto> getOrdersByClientId(int clientId) throws ServiceException;
    OrderDto getById(int id) throws ServiceException;

}