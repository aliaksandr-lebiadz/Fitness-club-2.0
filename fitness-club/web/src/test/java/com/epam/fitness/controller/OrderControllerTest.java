package com.epam.fitness.controller;

import com.epam.fitness.config.SpringWebMvcConfig;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.controller.ControllerAdviceImpl;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.OrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringWebMvcConfig.class)
public class OrderControllerTest{

    private static final String ORDERS_PAGE_REQUEST = "/order/list";
    private static final String ORDERS_PAGE_VIEW_NAME = "orders";
    private static final String FEEDBACK_REQUEST = "/order/feedback";
    private static final String SET_NUTRITION_REQUEST = "/order/setNutrition";
    private static final String REFERER_HEADER = "referer";
    private static final String CURRENT_PAGE = "http://localhost:8080/fitness/assignmentlist";
    private static final int CLIENT_ID = 3;
    private static final List<Order> EXPECTED_ORDERS;
    private static final User USER = new User(CLIENT_ID, "email", "pass", UserRole.CLIENT,
            "first", "second", 1);
    private static final String FEEDBACK_PARAMETER = "feedback";
    private static final String FEEDBACK = "validFeedback";
    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final int ORDER_ID = 16;
    private static final NutritionType NUTRITION_TYPE = NutritionType.HIGH_CALORIE;
    private static final String ERROR_PAGE_URL = "/error";

    static{
        Order.Builder builder = Order.createBuilder();
        EXPECTED_ORDERS = Arrays.asList(
                builder.setId(1).build(),
                builder.setId(2).build(),
                builder.setId(3).build());
    }

    private MockMvc mockMvc;

    @Mock
    private OrderService service;
    @Mock
    private ControllerUtils utils;
    @InjectMocks
    private OrderController orderController;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(orderController)
                .setControllerAdvice(new ControllerAdviceImpl())
                .build();
    }

    @Before
    public void createMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        when(utils.getCurrentUser()).thenReturn(Optional.of(USER));
        when(service.getOrdersOfClient(USER)).thenReturn(EXPECTED_ORDERS);
        doNothing().when(service).updateFeedbackById(ORDER_ID, FEEDBACK);
        doNothing().when(service).updateNutritionById(ORDER_ID, NUTRITION_TYPE);
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void testGetOrdersPageWhenUserIsAuthorizedAsClient() throws Exception{
        //given
        final String orderListAttribute = "orderList";

        //when
        mockMvc.perform(get(ORDERS_PAGE_REQUEST))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists(orderListAttribute))
                .andExpect(model().attribute(orderListAttribute, EXPECTED_ORDERS))
                .andExpect(view().name(ORDERS_PAGE_VIEW_NAME));

        //then
        verify(service, times(1)).getOrdersOfClient(USER);
        verify(utils, times(1)).getCurrentUser();
    }

    @Ignore
    @Test
    @WithMockUser(authorities = { "TRAINER", "ADMIN"} )
    public void testGetOrdersPageShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsClient() throws Exception{
        //given

        //when
        mockMvc.perform(get(ORDERS_PAGE_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void testFeedbackWhenValidFeedbackSuppliedAndUserIsAuthorizedAsClient() throws Exception{
        //given

        //when
        mockMvc.perform(post(FEEDBACK_REQUEST)
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID))
                .param(FEEDBACK_PARAMETER, FEEDBACK))
                .andExpect(redirectedUrl(ORDERS_PAGE_REQUEST));

        //then
        verify(service, times(1)).updateFeedbackById(ORDER_ID, FEEDBACK);
    }

    @Test
    @WithMockUser(authorities = { "TRAINER", "ADMIN" } )
    public void testFeedbackShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsClient() throws Exception{
        //given

        //when
        mockMvc.perform(post(FEEDBACK_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void testSetNutritionWhenUserIsAuthorizedAsTrainer() throws Exception{
        //given
        final String nutritionTypeParameter = "nutrition_type";

        //when
        mockMvc.perform(post(SET_NUTRITION_REQUEST)
                .param(nutritionTypeParameter, NUTRITION_TYPE.toString())
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID))
                .header(REFERER_HEADER, CURRENT_PAGE))
                .andExpect(redirectedUrl(CURRENT_PAGE));

        //then
        verify(service, times(1)).updateNutritionById(ORDER_ID, NUTRITION_TYPE);
    }

    @Test
    @WithMockUser(authorities = { "CLIENT", "ADMIN" } )
    public void testSetNutritionShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsTrainer() throws Exception{
        //given

        //when
        mockMvc.perform(post(SET_NUTRITION_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(service, utils);
        reset(service, utils);
    }

}