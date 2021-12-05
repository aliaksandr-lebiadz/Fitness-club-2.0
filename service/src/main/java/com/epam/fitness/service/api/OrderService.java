
package com.epam.fitness.service.api;

import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    @Transactional
    void create(UserDto client, int membershipId) throws ServiceException;
    void update(OrderDto order) throws ServiceException;
    List<OrderDto> getOrdersOfTrainerClient(int clientId, UserDto trainer) throws ServiceException;
    List<OrderDto> getOrdersOfClient(UserDto client) throws ServiceException;
    Optional<OrderDto> getById(int id);

}