package com.epam.fitness.service.impl;

import com.epam.fitness.dao.factory.DaoFactory;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.DaoException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.GymMembershipDao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.utils.OrderUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private GymMembershipDao gymMembershipDao;
    private OrderUtils utils;

    public OrderServiceImpl(DaoFactory factory, OrderUtils utils){
        this.orderDao = factory.createOrderDao();
        this.gymMembershipDao = factory.createGymMembershipDao();
        this.utils = utils;
    }

    @Override
    public void create(User client, int membershipId) throws ServiceException {
        try {
            Optional<GymMembership> gymMembershipOptional = gymMembershipDao.findById(membershipId);
            GymMembership gymMembership = gymMembershipOptional
                    .orElseThrow(() -> new ServiceException("Gym membership with the id " + membershipId + " isn't found!"));
            int monthsAmount = gymMembership.getMonthsAmount();
            BigDecimal initialPrice = gymMembership.getPrice();
            int clientDiscount = client.getDiscount();
            BigDecimal totalPrice = utils.calculatePriceWithDiscount(initialPrice, clientDiscount);
            Date endDate = utils.getDateAfterMonthsAmount(monthsAmount);
            int clientId = client.getId();
            Order order = new Order(clientId, endDate, totalPrice);
            orderDao.save(order);
        } catch(DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Order> getClientOrdersWithTrainerId(int clientId, int trainerId) throws ServiceException {
        try {
            return orderDao.findClientOrdersWithTrainerId(clientId, trainerId);
        } catch(DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Order> getOrdersByClientId(int clientId) throws ServiceException {
        try{
            return orderDao.findOrdersByClientId(clientId);
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void updateFeedbackById(int id, String feedback) throws ServiceException {
        try{
            Optional<Order> orderOptional = orderDao.findById(id);
            Order order = orderOptional
                    .orElseThrow(() -> new ServiceException("Order with id " + id + " not found!"));
            order.setFeedback(feedback);
            orderDao.save(order);
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void updateNutritionById(int id, NutritionType nutritionType) throws ServiceException {
        try{
            Optional<Order> orderOptional = orderDao.findById(id);
            Order order = orderOptional
                    .orElseThrow(() -> new ServiceException("Order with id " + id + " not found!"));
            order.setNutritionType(nutritionType);
            orderDao.save(order);
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}