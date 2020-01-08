package com.epam.fitness.controller;

import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.validator.api.OrderValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class OrderControllerTest extends AbstractControllerTest{

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
    private static final String VALID_FEEDBACK = "validFeedback";
    private static final String INVALID_FEEDBACK = "invalidFeedback";
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

    @Autowired
    private OrderService service;

    @Autowired
    private OrderValidator validator;

    @Before
    public void createMocks() throws ServiceException {
        when(service.getOrdersByClientId(CLIENT_ID)).thenReturn(EXPECTED_ORDERS);
        when(validator.isFeedbackValid(VALID_FEEDBACK)).thenReturn(true);
        when(validator.isFeedbackValid(INVALID_FEEDBACK)).thenReturn(false);
        doNothing().when(service).updateFeedbackById(ORDER_ID, VALID_FEEDBACK);
        doNothing().when(service).updateNutritionById(ORDER_ID, NUTRITION_TYPE);
    }

    @Test
    public void testGetOrdersPage() throws Exception{
        final String userAttribute = "user";
        final String orderListAttribute = "orderList";

        mockMvc.perform(get(ORDERS_PAGE_REQUEST)
                .sessionAttr(userAttribute, USER))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists(orderListAttribute))
                .andExpect(model().attribute(orderListAttribute, EXPECTED_ORDERS))
                .andExpect(view().name(ORDERS_PAGE_VIEW_NAME));

        verify(service, times(1)).getOrdersByClientId(CLIENT_ID);
    }

    @Test
    public void testFeedbackWhenValidFeedbackSupplied() throws Exception{
        mockMvc.perform(post(FEEDBACK_REQUEST)
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID))
                .param(FEEDBACK_PARAMETER, VALID_FEEDBACK))
                .andExpect(redirectedUrl(ORDERS_PAGE_REQUEST));

        verify(service, times(1)).updateFeedbackById(ORDER_ID, VALID_FEEDBACK);
        verify(validator, times(1)).isFeedbackValid(VALID_FEEDBACK);
    }

    @Test
    public void testFeedbackWhenInvalidFeedbackSupplied() throws Exception{
        mockMvc.perform(post(FEEDBACK_REQUEST)
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID))
                .param(FEEDBACK_PARAMETER, INVALID_FEEDBACK))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        verify(validator, times(1)).isFeedbackValid(INVALID_FEEDBACK);
        verifyNoInteractions(service);
    }

    @Test
    public void testSetNutrition() throws Exception{
        final String nutritionTypeParameter = "nutrition_type";
        mockMvc.perform(post(SET_NUTRITION_REQUEST)
                .param(nutritionTypeParameter, NUTRITION_TYPE.getValue())
                .param(ORDER_ID_PARAMETER, String.valueOf(ORDER_ID))
                .header(REFERER_HEADER, CURRENT_PAGE))
                .andExpect(redirectedUrl(CURRENT_PAGE));

        verify(service, times(1)).updateNutritionById(ORDER_ID, NUTRITION_TYPE);
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(service, validator);
        reset(service, validator);
    }

}