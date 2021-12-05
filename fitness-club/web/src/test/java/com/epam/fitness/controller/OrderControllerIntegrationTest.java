package com.epam.fitness.controller;

import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class OrderControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String ORDERS_PAGE_REQUEST = "/order/list";
    private static final String ORDERS_PAGE_VIEW_NAME = "orders";
    private static final String FEEDBACK_REQUEST = "/order/feedback";
    private static final String SET_NUTRITION_REQUEST = "/order/setNutrition";
    private static final String REFERER_HEADER = "referer";
    private static final String CURRENT_PAGE = "http://localhost:8080/fitness/assignmentlist";
    private static final int CLIENT_ID = 3;
    private static final User CLIENT = new User(CLIENT_ID, "email", "pass", UserRole.CLIENT,
            "first", "second", 1);
    private static final Order ORDER = Order.createBuilder()
            .setNutritionType(NutritionType.MEDIUM_CALORIE)
            .setBeginDate(Date.valueOf("2020-11-05"))
            .setEndDate(Date.valueOf("2021-05-13"))
            .setPrice(BigDecimal.ONE)
            .build();
    private static final String FEEDBACK = "validFeedback";
    private static final Order ORDER_WITH_FEEDBACK = Order.createBuilder()
            .setNutritionType(NutritionType.MEDIUM_CALORIE)
            .setBeginDate(Date.valueOf("2020-11-05"))
            .setEndDate(Date.valueOf("2021-05-13"))
            .setPrice(BigDecimal.ONE)
            .setFeedback(FEEDBACK)
            .build();
    private static final NutritionType NUTRITION_TYPE = NutritionType.HIGH_CALORIE;
    private static final Order ORDER_WITH_NEW_NUTRITION_TYPE = Order.createBuilder()
            .setNutritionType(NUTRITION_TYPE)
            .setBeginDate(Date.valueOf("2020-11-05"))
            .setEndDate(Date.valueOf("2021-05-13"))
            .setPrice(BigDecimal.ONE)
            .build();
    private static final String FEEDBACK_PARAMETER = "feedback";
    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final String NUTRITION_TYPE_PARAMETER = "nutrition_type";
    private static final int EXISTENT_ORDER_ID = 16;
    private static final int NONEXISTENT_ORDER_ID = 20;
    private static final String ERROR_PAGE_URL = "/error";

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Before
    public void setUp(){
        when(userDao.findUserByEmail("client@gmail.com")).thenReturn(Optional.of(CLIENT));
        when(userDao.findById(CLIENT_ID)).thenReturn(Optional.of(CLIENT));
        when(orderDao.findById(EXISTENT_ORDER_ID)).thenReturn(Optional.of(ORDER));
        when(orderDao.findById(NONEXISTENT_ORDER_ID)).thenReturn(Optional.empty());
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void getOrdersPage_withTrainerUserRole_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get(ORDERS_PAGE_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT", username = "client@gmail.com")
    public void getOrdersPage_withClientUserRole_ordersPageView() throws Exception {
        //given
        when(orderDao.findOrdersOfClient(any(User.class))).thenReturn(Collections.singletonList(ORDER));

        //when
        mockMvc.perform(get(ORDERS_PAGE_REQUEST))
                .andExpect(status().isOk())
                .andExpect(model().attribute("orderList", hasSize(1)))
                .andExpect(model().attribute("orderList", hasItem(allOf(
                        hasProperty("price", is(ORDER.getPrice())),
                        hasProperty("beginDate", is(ORDER.getBeginDate())),
                        hasProperty("endDate", is(ORDER.getEndDate())),
                        hasProperty("nutritionType", is(ORDER.getNutritionType()))
                ))))
                .andExpect(view().name(ORDERS_PAGE_VIEW_NAME));

        //then
        unwrapAndVerify(userDao, times(1)).findUserByEmail("client@gmail.com");
        unwrapAndVerify(userDao, times(1)).findById(CLIENT_ID);
        unwrapAndVerify(orderDao, times(1)).findOrdersOfClient(any(User.class));
    }

    @Test
    @WithAnonymousUser
    public void sendFeedback_withAnonymousUser_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(FEEDBACK_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void sendFeedback_withInvalidFeedbackLength_redirectOnErrorPage() throws Exception {
        //given
        final String invalidFeedback = "feedback";

        //when
        mockMvc.perform(post(FEEDBACK_REQUEST)
                .param(ORDER_ID_PARAMETER, String.valueOf(EXISTENT_ORDER_ID))
                .param(FEEDBACK_PARAMETER, invalidFeedback))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void sendFeedback_withNonexistentOrderId_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(FEEDBACK_REQUEST)
                .param(ORDER_ID_PARAMETER, String.valueOf(NONEXISTENT_ORDER_ID))
                .param(FEEDBACK_PARAMETER, FEEDBACK))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
        unwrapAndVerify(orderDao, times(1)).findById(NONEXISTENT_ORDER_ID);
    }


    @Test
    @WithMockUser(authorities = "CLIENT")
    public void sendFeedback_withExistentOrderId_orderSuccessfullyUpdatedAndRedirectOnOrdersPage() throws Exception {
        //given
        when(orderDao.save(any(Order.class))).thenReturn(ORDER_WITH_FEEDBACK);

        //when
        mockMvc.perform(post(FEEDBACK_REQUEST)
                .param(ORDER_ID_PARAMETER, String.valueOf(EXISTENT_ORDER_ID))
                .param(FEEDBACK_PARAMETER, FEEDBACK))
                .andExpect(redirectedUrl("/order/list"));

        //then
        unwrapAndVerify(orderDao, times(1)).findById(EXISTENT_ORDER_ID);
        unwrapAndVerify(orderDao, times(1)).save(any(Order.class));
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void setNutrition_withClientUserRole_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(SET_NUTRITION_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void setNutrition_withNonexistentOrderId_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(SET_NUTRITION_REQUEST)
                .param(ORDER_ID_PARAMETER, String.valueOf(NONEXISTENT_ORDER_ID))
                .param(NUTRITION_TYPE_PARAMETER, String.valueOf(NUTRITION_TYPE)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
        unwrapAndVerify(orderDao, times(1)).findById(NONEXISTENT_ORDER_ID);
    }


    @Test
    @WithMockUser(authorities = "TRAINER")
    public void setNutrition_withExistentOrderId_orderSuccessfullyUpdatedAndRedirectOnOrdersPage() throws Exception {
        //given
        when(orderDao.save(any(Order.class))).thenReturn(ORDER_WITH_NEW_NUTRITION_TYPE);

        //when
        mockMvc.perform(post(SET_NUTRITION_REQUEST)
                .header(REFERER_HEADER, CURRENT_PAGE)
                .param(ORDER_ID_PARAMETER, String.valueOf(EXISTENT_ORDER_ID))
                .param(NUTRITION_TYPE_PARAMETER, String.valueOf(NUTRITION_TYPE)))
                .andExpect(redirectedUrl(CURRENT_PAGE));

        //then
        unwrapAndVerify(orderDao, times(1)).findById(EXISTENT_ORDER_ID);
        unwrapAndVerify(orderDao, times(1)).save(any(Order.class));
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(userDao, orderDao);
        reset(userDao, orderDao);
    }

}