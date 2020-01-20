package com.epam.fitness.service.api;

import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderService {

    @Transactional
    void create(User client, int membershipId) throws ServiceException;
    void updateFeedbackById(int id, String feedback) throws ServiceException;
    void updateNutritionById(int id, NutritionType nutritionType) throws ServiceException;
    List<Order> getOrdersOfTrainerClient(int clientId, User trainer);
    List<Order> getOrdersOfClient(User client);

}