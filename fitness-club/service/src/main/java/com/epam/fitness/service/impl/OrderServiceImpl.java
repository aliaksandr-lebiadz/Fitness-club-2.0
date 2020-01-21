package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private Dao<GymMembership> gymMembershipDao;
    private OrderUtils utils;
    private UserDao userDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            Dao<GymMembership> gymMembershipDao,
                            UserDao userDao,
                            OrderUtils utils){
        this.orderDao = orderDao;
        this.gymMembershipDao = gymMembershipDao;
        this.userDao = userDao;
        this.utils = utils;
    }

    @Override
    public void create(User client, int membershipId) throws ServiceException {
        Optional<GymMembership> gymMembershipOptional = gymMembershipDao.findById(membershipId);
        GymMembership gymMembership = gymMembershipOptional
                .orElseThrow(() -> new ServiceException("Gym membership with the id " + membershipId + " isn't found!"));
        BigDecimal totalPrice = calculateTotalPrice(gymMembership, client);
        Date endDate = calculateEndDate(gymMembership);
        Optional<User> randomTrainerOptional = userDao.getRandomTrainer();
        User trainer = randomTrainerOptional
                .orElseThrow(() -> new ServiceException("Trainers aren't found!"));
        Order order = createOrderWithCurrentBeginDate(client, trainer, endDate, totalPrice);
        orderDao.save(order);
    }

    @Override
    public List<Order> getOrdersOfTrainerClient(int clientId, User trainer) {
        return orderDao.findOrdersOfTrainerClient(clientId, trainer);
    }

    @Override
    public List<Order> getOrdersOfClient(User client) {
        return orderDao.findOrdersOfClient(client);
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

    private Date calculateEndDate(GymMembership gymMembership){
        int monthsAmount = gymMembership.getMonthsAmount();
        return utils.getDateAfterMonthsAmount(monthsAmount);
    }

    private Order createOrderWithCurrentBeginDate(User client, User trainer, Date endDate, BigDecimal price){
        Date beginDate = new Date();
        return Order.createBuilder()
                .setClient(client)
                .setTrainer(trainer)
                .setBeginDate(beginDate)
                .setEndDate(endDate)
                .setPrice(price)
                .build();
    }
}