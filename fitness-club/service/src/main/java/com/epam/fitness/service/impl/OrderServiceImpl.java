package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.UserDto;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private Dao<GymMembership> gymMembershipDao;
    private OrderUtils utils;
    private UserDao userDao;
    private DtoMapper<Order, OrderDto> orderMapper;
    private DtoMapper<User, UserDto> userMapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            Dao<GymMembership> gymMembershipDao,
                            UserDao userDao,
                            OrderUtils utils,
                            DtoMapper<Order, OrderDto> orderMapper,
                            DtoMapper<User, UserDto> userMapper){
        this.orderDao = orderDao;
        this.gymMembershipDao = gymMembershipDao;
        this.userDao = userDao;
        this.utils = utils;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    @Override
    public void create(int clientId, GymMembershipDto gymMembershipDto) throws ServiceException {
        Optional<User> clientOptional = userDao.findById(clientId);
        User client = clientOptional
                .orElseThrow(() -> new ServiceException("Client with id " + clientId + "not found!"));
        int gymMembershipId = gymMembershipDto.getId();
        Optional<GymMembership> gymMembershipOptional = gymMembershipDao.findById(gymMembershipId);
        GymMembership gymMembership = gymMembershipOptional
                .orElseThrow(() -> new ServiceException("Gym membership with id " + gymMembershipId + "not found!"));
        BigDecimal totalPrice = calculateTotalPrice(gymMembership, client);
        LocalDateTime endDate = calculateEndDate(gymMembership);
        Optional<User> randomTrainerOptional = userDao.getRandomTrainer();
        User trainer = randomTrainerOptional
                .orElseThrow(() -> new ServiceException("Trainers aren't found!"));
        Order order = createOrderWithCurrentBeginDate(client, trainer, endDate, totalPrice);
        orderDao.save(order);
    }

    @Override
    public List<OrderDto> getOrdersOfTrainerClient(int clientId, UserDto trainerDto){
            User trainer = userMapper.mapToEntity(trainerDto);
            List<Order> orders = orderDao.findOrdersOfTrainerClient(clientId, trainer);
            return mapOrdersToDto(orders);
    }

    @Override
    public List<OrderDto> getOrdersByClientId(int clientId) throws ServiceException{
        Optional<User> clientOptional = userDao.findById(clientId);
        User client = clientOptional
                .orElseThrow(() ->  new ServiceException("Client with id " + clientId + " not found!"));
        List<Order> orders =  orderDao.findOrdersOfClient(client);
        return mapOrdersToDto(orders);
    }

    @Override
    public OrderDto getById(int id) throws ServiceException {
        Optional<Order> orderOptional = orderDao.findById(id);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + id + " not found!"));
        return orderMapper.mapToDto(order);
    }

    @Override
    public void updateById(int id, OrderDto orderDto) throws ServiceException{
        Optional<Order> orderOptional = orderDao.findById(id);
        Order order = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + id + " not found!"));
        String feedback = orderDto.getFeedback();
        if(Objects.nonNull(feedback)){
            order.setFeedback(feedback);
        }
        NutritionType nutritionType = orderDto.getNutritionType();
        if(Objects.nonNull(nutritionType)){
            order.setNutritionType(nutritionType);
        }
        orderDao.save(order);
    }

    private BigDecimal calculateTotalPrice(GymMembership gymMembership, User client){
        BigDecimal initialPrice = gymMembership.getPrice();
        int clientDiscount = client.getDiscount();
        return  utils.calculatePriceWithDiscount(initialPrice, clientDiscount);
    }

    private LocalDateTime calculateEndDate(GymMembership gymMembership){
        int monthsAmount = gymMembership.getMonthsAmount();
        return utils.getDateAfterMonthsAmount(monthsAmount);
    }

    private Order createOrderWithCurrentBeginDate(User client, User trainer, LocalDateTime endDate, BigDecimal price){
        LocalDateTime beginDate = LocalDateTime.now();
        return Order.createBuilder()
                .setClient(client)
                .setTrainer(trainer)
                .setBeginDate(beginDate)
                .setEndDate(endDate)
                .setPrice(price)
                .build();
    }

    private List<OrderDto> mapOrdersToDto(List<Order> orders){
        return orders.stream()
                .map(order -> orderMapper.mapToDto(order))
                .collect(Collectors.toList());
    }
}