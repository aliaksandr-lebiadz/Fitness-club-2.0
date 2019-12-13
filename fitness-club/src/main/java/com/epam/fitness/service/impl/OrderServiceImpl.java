package com.epam.fitness.service.impl;

import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.GymMembershipDao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private GymMembershipDao gymMembershipDao;
    private OrderUtils utils;

    @Autowired
    public OrderServiceImpl(@Qualifier("postgreSqlOrderDao") OrderDao orderDao,
                            GymMembershipDao gymMembershipDao,
                            OrderUtils utils){
        this.orderDao = orderDao;
        this.gymMembershipDao = gymMembershipDao;
        this.utils = utils;
    }

    @Override
    public void create(User client, int membershipId) throws ServiceException {
        Optional<GymMembership> gymMembershipOptional = gymMembershipDao.findById(membershipId);
        GymMembership gymMembership = gymMembershipOptional
                .orElseThrow(() -> new ServiceException("Gym membership with the id " + membershipId + " isn't found!"));
        BigDecimal totalPrice = calculateTotalPrice(gymMembership, client);
        int monthsAmount = gymMembership.getMonthsAmount();
        Date endDate = utils.getDateAfterMonthsAmount(monthsAmount);
        int clientId = client.getId();
        Date now = new Date();
        Order order = Order.createBuilder()
                .setClientId(clientId)
                .setBeginDate(now)
                .setEndDate(endDate)
                .setPrice(totalPrice)
                .build();
        orderDao.save(order);
    }

    @Override
    public List<Order> getClientOrdersWithTrainerId(int clientId, int trainerId) {
        return orderDao.findClientOrdersWithTrainerId(clientId, trainerId);
    }

    @Override
    public List<Order> getOrdersByClientId(int clientId) {
        return orderDao.findOrdersByClientId(clientId);
    }

    @Override
    public void updateFeedbackById(int id, String feedback) throws ServiceException {
        Optional<Order> orderOptional = orderDao.findById(id);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + id + " not found!"));
        order.setFeedback(feedback);
        orderDao.save(order);
    }

    @Override
    public void updateNutritionById(int id, NutritionType nutritionType) throws ServiceException {
        Optional<Order> orderOptional = orderDao.findById(id);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + id + " not found!"));
        order.setNutritionType(nutritionType);
        orderDao.save(order);
    }

    private BigDecimal calculateTotalPrice(GymMembership gymMembership, User client){
        BigDecimal initialPrice = gymMembership.getPrice();
        int clientDiscount = client.getDiscount();
        return  utils.calculatePriceWithDiscount(initialPrice, clientDiscount);
    }
}