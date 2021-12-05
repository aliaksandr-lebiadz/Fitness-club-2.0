package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dto.mapper.impl.OrderDtoMapper;
import com.epam.fitness.dto.mapper.impl.UserDtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.EntityMappingException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.utils.OrderUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private static final int EXISTENT_ID = 2;
    private static final int NONEXISTENT_ID = 5;
    private static final Order ORDER = Order.createBuilder()
            .setId(EXISTENT_ID)
            .setFeedback("feedback")
            .build();
    private static final OrderDto ORDER_DTO = new OrderDto(EXISTENT_ID, "feedback");
    private static final int EXISTENT_GYM_MEMBERSHIP_ID = 11;
    private static final int NONEXISTENT_GYM_MEMBERSHIP_ID = 1;
    private static final BigDecimal PRICE_WITH_DISCOUNT = BigDecimal.valueOf(1);
    private static final Date END_DATE = Date.valueOf("2020-11-11");
    private static final UserDto USER_DTO = new UserDto(1, "email", "pass", UserRole.CLIENT, "firstName", "secondName", 5);
    private static final User USER = new User(1, "email", "pass", UserRole.CLIENT, "firstName", "secondName", 5);
    private static final GymMembership GYM_MEMBERSHIP = new GymMembership(1, 3, BigDecimal.valueOf(11.3));

    @Mock
    private OrderDao orderDao;

    @Mock
    private Dao<GymMembership> gymMembershipDao;

    @Mock
    private UserDao userDao;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Mock
    private OrderDtoMapper orderDtoMapper;

    @Mock
    private OrderUtils utils;

    @InjectMocks
    private OrderServiceImpl service;

    @Before
    public void setUp() throws EntityMappingException {
        when(orderDao.findById(EXISTENT_ID)).thenReturn(Optional.of(ORDER));
        when(orderDao.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());

        when(gymMembershipDao.findById(EXISTENT_GYM_MEMBERSHIP_ID)).thenReturn(Optional.of(GYM_MEMBERSHIP));
        when(gymMembershipDao.findById(NONEXISTENT_GYM_MEMBERSHIP_ID)).thenReturn(Optional.empty());

        when(userDtoMapper.mapToEntity(USER_DTO)).thenReturn(USER);

        when(utils.calculatePriceWithDiscount(GYM_MEMBERSHIP.getPrice(), USER.getDiscount())).thenReturn(PRICE_WITH_DISCOUNT);
        when(utils.getDateAfterMonthsAmount(GYM_MEMBERSHIP.getMonthsAmount())).thenReturn(END_DATE);
    }

    @Test
    public void getById_checkWithExistentId_optionalOfFoundOrder() {
        //given
        when(orderDtoMapper.mapToDto(eq(ORDER))).thenReturn(ORDER_DTO);

        //when
        Optional<OrderDto> actualOptional = service.getById(EXISTENT_ID);

        //then
        Assert.assertTrue(actualOptional.isPresent());
        OrderDto actual = actualOptional.get();
        Assert.assertEquals(ORDER.getId(), actual.getId());

        verify(orderDao, times(1)).findById(EXISTENT_ID);
        verify(orderDtoMapper, times(1)).mapToDto(ORDER);
        verifyNoMoreInteractions(orderDao, orderDtoMapper);
    }

    @Test
    public void getById_checkWithNonexistentId_emptyOptional() {
        //given

        //when
        Optional<OrderDto> actualOptional = service.getById(NONEXISTENT_ID);

        //then
        Assert.assertFalse(actualOptional.isPresent());

        verify(orderDao, times(1)).findById(NONEXISTENT_ID);
        verifyNoMoreInteractions(orderDao, orderDtoMapper);
    }

    @Test(expected = ServiceException.class)
    public void create_withNonexistentGymMembershipId_serviceException() throws ServiceException {
        //given

        //when
        service.create(USER_DTO, NONEXISTENT_GYM_MEMBERSHIP_ID);

        //then
    }

    @Test
    @Ignore
    public void create_withValidClientAndGymMembershipId_orderSuccessfullyCreated() throws ServiceException, EntityMappingException {
        //given
        when(userDao.getRandomTrainer()).thenReturn(USER);
        final Order order = Order.createBuilder()
                .setClient(USER)
                .setTrainer(USER)
                .setEndDate(END_DATE)
                .setPrice(PRICE_WITH_DISCOUNT)
                .build();
        when(orderDao.save(any(Order.class))).thenReturn(order);

        //when
        service.create(USER_DTO, EXISTENT_GYM_MEMBERSHIP_ID);

        //then
        verify(userDtoMapper, times(1)).mapToEntity(USER_DTO);
        verify(gymMembershipDao, times(1)).findById(EXISTENT_GYM_MEMBERSHIP_ID);
        verify(userDao, times(1)).getRandomTrainer();
        verify(utils, times(1)).calculatePriceWithDiscount(GYM_MEMBERSHIP.getPrice(), USER.getDiscount());
        verify(utils, times(1)).getDateAfterMonthsAmount(GYM_MEMBERSHIP.getMonthsAmount());
        verify(orderDao, times(1)).save(eq(order));
        verifyNoMoreInteractions(userDtoMapper, gymMembershipDao, userDao, utils, orderDao);
    }

    @After
    public void tearDown() {
        reset(userDtoMapper, gymMembershipDao, userDao, utils, orderDao, orderDtoMapper);
        verifyNoMoreInteractions(userDtoMapper, gymMembershipDao, userDao, utils, orderDao, orderDtoMapper);
    }

}
