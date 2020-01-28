package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.EntityMappingException;
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
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private Dao<GymMembership> gymMembershipDao;
    private OrderUtils utils;
    private UserDao userDao;
    private DtoMapper<Order, OrderDto> orderDtoMapper;
    private DtoMapper<User, UserDto> userDtoMapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao,
                            Dao<GymMembership> gymMembershipDao,
                            UserDao userDao,
                            OrderUtils utils,
                            DtoMapper<Order, OrderDto> orderDtoMapper,
                            DtoMapper<User, UserDto> userDtoMapper){
        this.orderDao = orderDao;
        this.gymMembershipDao = gymMembershipDao;
        this.userDao = userDao;
        this.utils = utils;
        this.orderDtoMapper = orderDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public void create(UserDto clientDto, int membershipId) throws ServiceException {
        User client = mapUserDtoToEntity(clientDto);
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
    public List<OrderDto> getOrdersOfTrainerClient(int clientId, UserDto trainerDto) throws ServiceException{
        try{
            User trainer = userDtoMapper.mapToEntity(trainerDto);
            List<Order> orders = orderDao.findOrdersOfTrainerClient(clientId, trainer);
            return mapOrdersToDto(orders);
        } catch (EntityMappingException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<OrderDto> getOrdersOfClient(UserDto clientDto) throws ServiceException{
        try{
            User client = userDtoMapper.mapToEntity(clientDto);
            List<Order> orders =  orderDao.findOrdersOfClient(client);
            return mapOrdersToDto(orders);
        } catch (EntityMappingException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<OrderDto> getById(int id) {
        Optional<Order> orderOptional = orderDao.findById(id);
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            OrderDto orderDto = orderDtoMapper.mapToDto(order);
            return Optional.of(orderDto);
        } else{
            return Optional.empty();
        }
    }

    @Override
    public void update(OrderDto orderDto) throws ServiceException{
        try{
            Order order = orderDtoMapper.mapToEntity(orderDto);
            orderDao.save(order);
        } catch (EntityMappingException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
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

    private List<OrderDto> mapOrdersToDto(List<Order> orders){
        return orders.stream()
                .map(order -> orderDtoMapper.mapToDto(order))
                .collect(Collectors.toList());
    }

    private User mapUserDtoToEntity(UserDto userDto) throws ServiceException{
        try{
            return userDtoMapper.mapToEntity(userDto);
        } catch (EntityMappingException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}