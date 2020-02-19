package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.DtoMappingException;
import com.epam.fitness.exception.EntityNotFoundException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.utils.OrderUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private static final int ORDER_ID = 5;
    private static final int EXISTENT_CLIENT_ID = 1;
    private static final int CLIENT_DISCOUNT = 52;
    private static final User CLIENT = new User(EXISTENT_CLIENT_ID, "client", "client", UserRole.CLIENT,
            "Alex", "Lopez", CLIENT_DISCOUNT);
    private static final int EXISTENT_TRAINER_ID = 22;
    private static final User TRAINER = new User(EXISTENT_TRAINER_ID, "trainer", "tr", UserRole.TRAINER,
            "Trainer", "Trainer", 22);
    private static final LocalDateTime ORDER_BEGIN_DATE = LocalDateTime.parse("2020-11-12T18:25:35");
    private static final LocalDateTime ORDER_END_DATE = LocalDateTime.parse("2021-11-12T18:25:35");
    private static final BigDecimal ORDER_PRICE = BigDecimal.valueOf(70.52);
    private static final Order ORDER = Order
            .createBuilder()
            .setClient(CLIENT)
            .setTrainer(TRAINER)
            .setBeginDate(ORDER_BEGIN_DATE)
            .setEndDate(ORDER_END_DATE)
            .setPrice(ORDER_PRICE)
            .build();
    private static final OrderDto ORDER_DTO =
            new OrderDto(ORDER_ID, ORDER_BEGIN_DATE, ORDER_END_DATE, ORDER_PRICE);

    private static final List<Order> ORDERS = Collections.singletonList(ORDER);
    private static final List<OrderDto> ORDERS_DTO = Collections.singletonList(ORDER_DTO);

    private static final int NONEXISTENT_ORDER_ID = 25;
    private static final int NONEXISTENT_CLIENT_ID = 3;
    private static final int NONEXISTENT_TRAINER_ID = 11;
    private static final int EXISTENT_GYM_MEMBERSHIP_ID = 15;
    private static final int NONEXISTENT_GYM_MEMBERSHIP_ID = 32;
    private static final BigDecimal GYM_MEMBERSHIP_PRICE = BigDecimal.valueOf(152.11);
    private static final int GYM_MEMBERSHIP_MONTHS_AMOUNT = 12;
    private static final GymMembership GYM_MEMBERSHIP =
            new GymMembership(EXISTENT_GYM_MEMBERSHIP_ID, GYM_MEMBERSHIP_MONTHS_AMOUNT, GYM_MEMBERSHIP_PRICE);

    private static final int ORDER_ID_FOR_UPDATE = 11;
    private static final BigDecimal PRICE_FOR_UPDATE = BigDecimal.valueOf(11.22);
    private static final OrderDto ORDER_DTO_FOR_UPDATE = new OrderDto(ORDER_ID_FOR_UPDATE, ORDER_BEGIN_DATE, ORDER_END_DATE, PRICE_FOR_UPDATE);
    private static final Order ORDER_FOR_UPDATE = Order
            .createBuilder()
            .setBeginDate(ORDER_BEGIN_DATE)
            .setEndDate(ORDER_END_DATE)
            .setPrice(PRICE_FOR_UPDATE)
            .build();

    @Mock
    private OrderDao orderDao;
    @Mock
    private Dao<GymMembership> gymMembershipDao;
    @Mock
    private OrderUtils utils;
    @Mock
    private UserDao userDao;
    @Mock
    private DtoMapper<Order, OrderDto> orderMapper;
    @InjectMocks
    private OrderServiceImpl service;

    @Before
    public void createMocks() throws DtoMappingException {
        MockitoAnnotations.initMocks(this);

        when(userDao.findById(EXISTENT_CLIENT_ID)).thenReturn(Optional.of(CLIENT));
        when(userDao.findById(NONEXISTENT_CLIENT_ID)).thenReturn(Optional.empty());
        when(userDao.getRandomTrainer()).thenReturn(Optional.of(TRAINER));
        when(userDao.findById(EXISTENT_TRAINER_ID)).thenReturn(Optional.of(TRAINER));
        when(userDao.findById(NONEXISTENT_TRAINER_ID)).thenReturn(Optional.empty());

        when(orderDao.findById(ORDER_ID)).thenReturn(Optional.of(ORDER));
        when(orderDao.findById(NONEXISTENT_ORDER_ID)).thenReturn(Optional.empty());
        when(orderDao.findOrdersOfClient(CLIENT)).thenReturn(ORDERS);
        when(orderDao.save(ORDER)).thenReturn(ORDER);
        when(orderDao.findOrdersOfTrainerClient(EXISTENT_CLIENT_ID, TRAINER)).thenReturn(ORDERS);
        when(orderDao.save(ORDER_FOR_UPDATE)).thenReturn(ORDER_FOR_UPDATE);

        when(gymMembershipDao.findById(EXISTENT_GYM_MEMBERSHIP_ID)).thenReturn(Optional.of(GYM_MEMBERSHIP));
        when(gymMembershipDao.findById(NONEXISTENT_GYM_MEMBERSHIP_ID)).thenReturn(Optional.empty());

        when(orderMapper.mapToDto(ORDER)).thenReturn(ORDER_DTO);
        when(orderMapper.mapToDto(ORDERS)).thenReturn(ORDERS_DTO);
        when(orderMapper.mapToEntity(ORDER_DTO_FOR_UPDATE)).thenReturn(ORDER_FOR_UPDATE);
        when(orderMapper.mapToDto(ORDER_FOR_UPDATE)).thenReturn(ORDER_DTO_FOR_UPDATE);

        when(utils.calculatePriceWithDiscount(GYM_MEMBERSHIP_PRICE, CLIENT_DISCOUNT)).thenReturn(ORDER_PRICE);
        when(utils.getCurrentDateTime()).thenReturn(ORDER_BEGIN_DATE);
        when(utils.getDateAfterMonthsAmount(GYM_MEMBERSHIP_MONTHS_AMOUNT)).thenReturn(ORDER_END_DATE);
    }

    @Test
    public void getByIdWhenExistentIdSupplied() throws ServiceException {
        //given

        //when
        OrderDto actual = service.getById(ORDER_ID);

        //then
        assertEquals(ORDER_DTO, actual);

        verify(orderDao).findById(ORDER_ID);
        verify(orderMapper).mapToDto(ORDER);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdWithEntityNotFoundException() throws ServiceException {
        //given

        //when/then
        try{
            service.getById(NONEXISTENT_ORDER_ID);
        } finally {
            verify(orderDao).findById(NONEXISTENT_ORDER_ID);
        }
    }

    @Test
    public void getOrdersByClientIdWhenExistentClientIdSupplied() throws ServiceException {
        //given

        //when
        List<OrderDto> actual = service.getOrdersByClientId(EXISTENT_CLIENT_ID);

        //then
        assertThat(actual, is(equalTo(ORDERS_DTO)));

        verify(userDao).findById(EXISTENT_CLIENT_ID);
        verify(orderDao).findOrdersOfClient(CLIENT);
        verify(orderMapper).mapToDto(ORDERS);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getOrdersByClientIdWithEntityNotFoundException() throws ServiceException {
        //given

        //when/then
        try{
            service.getOrdersByClientId(NONEXISTENT_CLIENT_ID);
        } finally {
            verify(userDao).findById(NONEXISTENT_CLIENT_ID);
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void createWhenNonexistentGymMembershipIdSupplied() throws ServiceException{
        //given

        //when/then
        try{
            service.create(EXISTENT_CLIENT_ID, NONEXISTENT_GYM_MEMBERSHIP_ID);
        } finally {
            verify(userDao).findById(EXISTENT_CLIENT_ID);
            verify(gymMembershipDao).findById(NONEXISTENT_GYM_MEMBERSHIP_ID);
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void createWhenNonexistentClientIdSupplied() throws ServiceException{
        //given

        //when/then
        try{
            service.create(NONEXISTENT_CLIENT_ID, EXISTENT_GYM_MEMBERSHIP_ID);
        } finally {
            verify(userDao).findById(NONEXISTENT_CLIENT_ID);
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void createWhenRandomTrainerNotFound() throws ServiceException{
        //given
        when(userDao.getRandomTrainer()).thenReturn(Optional.empty());

        //when/then
        try{
            service.create(EXISTENT_CLIENT_ID, EXISTENT_GYM_MEMBERSHIP_ID);
        } finally {
            verify(userDao).findById(EXISTENT_CLIENT_ID);
            verify(gymMembershipDao).findById(EXISTENT_GYM_MEMBERSHIP_ID);
            verify(utils).calculatePriceWithDiscount(GYM_MEMBERSHIP_PRICE, CLIENT_DISCOUNT);
            verify(utils).getDateAfterMonthsAmount(GYM_MEMBERSHIP_MONTHS_AMOUNT);
            verify(userDao).getRandomTrainer();
        }
    }

    @Test
    public void create() throws ServiceException{
        //given

        //when
        OrderDto actual = service.create(EXISTENT_CLIENT_ID, EXISTENT_GYM_MEMBERSHIP_ID);

        //then
        assertEquals(ORDER_DTO, actual);

        verify(userDao).findById(EXISTENT_CLIENT_ID);
        verify(gymMembershipDao).findById(EXISTENT_GYM_MEMBERSHIP_ID);
        verify(utils).calculatePriceWithDiscount(GYM_MEMBERSHIP_PRICE, CLIENT_DISCOUNT);
        verify(utils).getDateAfterMonthsAmount(GYM_MEMBERSHIP_MONTHS_AMOUNT);
        verify(userDao).getRandomTrainer();
        verify(utils).getCurrentDateTime();
        verify(orderDao).save(ORDER);
        verify(orderMapper).mapToDto(ORDER);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getOrdersOfTrainerClientsWhenNonexistentTrainerIdSupplied() throws ServiceException{
        //given

        //when/then
        try{
            service.getOrdersOfTrainerClient(EXISTENT_CLIENT_ID, NONEXISTENT_TRAINER_ID);
        } finally {
            verify(userDao).findById(NONEXISTENT_TRAINER_ID);
        }
    }

    @Test
    public void getOrdersOfTrainerClients() throws ServiceException{
        //given

        //when
        List<OrderDto> actual = service.getOrdersOfTrainerClient(EXISTENT_CLIENT_ID, EXISTENT_TRAINER_ID);

        //then
        assertThat(actual, is(equalTo(ORDERS_DTO)));

        verify(userDao).findById(EXISTENT_TRAINER_ID);
        verify(orderDao).findOrdersOfTrainerClient(EXISTENT_CLIENT_ID, TRAINER);
        verify(orderMapper).mapToDto(ORDERS);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateByIdWhenNonexistentOrderIdSupplied() throws ServiceException{
        //given

        //when/then
        try{
            service.updateById(NONEXISTENT_ORDER_ID, ORDER_DTO);
        } finally {
            verify(orderDao).findById(NONEXISTENT_ORDER_ID);
        }
    }

    @Test
    public void updateById() throws ServiceException{
        //given
        when(orderDao.findById(ORDER_ID)).thenReturn(Optional.of(ORDER_FOR_UPDATE));

        //when
        OrderDto actual = service.updateById(ORDER_ID, ORDER_DTO_FOR_UPDATE);

        //then
        assertEquals(actual, ORDER_DTO_FOR_UPDATE);

        verify(orderDao).findById(ORDER_ID);
        verify(orderMapper).mapToEntity(ORDER_DTO_FOR_UPDATE);
        verify(orderDao).save(ORDER_FOR_UPDATE);
        verify(orderMapper).mapToDto(ORDER_FOR_UPDATE);
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(userDao, gymMembershipDao, utils, orderDao, orderMapper);
        reset(userDao, gymMembershipDao, utils, orderDao, orderMapper);
    }

}