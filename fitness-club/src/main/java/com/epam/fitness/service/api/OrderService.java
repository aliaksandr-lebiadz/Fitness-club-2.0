package com.epam.fitness.service.api;

import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;

import java.util.List;

public interface OrderService {

    void create(User client, int membershipId) throws ServiceException;
    List<Order> getClientOrdersWithTrainerId(int clientId, int trainerId) throws ServiceException;
    List<Order> getOrdersByClientId(int clientId) throws ServiceException;
    void updateFeedbackById(int id, String feedback) throws ServiceException;
    void updateNutritionById(int id, NutritionType nutritionType) throws ServiceException;

}