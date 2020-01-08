package com.epam.fitness.controller;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import com.epam.fitness.validator.api.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class ClientControllerTest extends AbstractControllerTest{

    private static final String CLIENTS_PAGE_REQUEST = "/client/list";
    private static final List<User> EXPECTED_CLIENTS = Arrays.asList(
            new User(5, "client@gmail.com", "client", UserRole.CLIENT, "Alex", "Kotov", 11),
            new User(11, "client2@gmail.com", "client", UserRole.CLIENT, "Mikhail", "Dubov", 1));
    private static final String CLIENTS_ATTRIBUTE = "userList";
    private static final String CLIENTS_PAGE_VIEW_NAME = "clients";
    private static final String SET_DISCOUNT_REQUEST = "/client/setDiscount";
    private static final String USER_ID_PARAMETER = "user_id";
    private static final int USER_ID = 5;
    private static final String DISCOUNT_PARAMETER = "discount";
    private static final int VALID_DISCOUNT = 97;
    private static final int INVALID_DISCOUNT = 101;
    private static final String CLIENTS_PAGE_URL = "/client/list";
    private static final String ERROR_PAGE_URL = "/error";

    @Autowired
    private UserService service;

    @Autowired
    private UserValidator validator;

    @Before
    public void createMocks() throws ServiceException {
        when(service.getAllClients()).thenReturn(EXPECTED_CLIENTS);
        doNothing().when(service).setUserDiscount(anyInt(), anyInt());
        when(validator.isDiscountValid(VALID_DISCOUNT)).thenReturn(true);
        when(validator.isDiscountValid(INVALID_DISCOUNT)).thenReturn(false);
    }

    @Test
    public void testGetClientsPage() throws Exception{
        mockMvc.perform(get(CLIENTS_PAGE_REQUEST))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists(CLIENTS_ATTRIBUTE))
                .andExpect(model().attribute(CLIENTS_ATTRIBUTE, EXPECTED_CLIENTS))
                .andExpect(view().name(CLIENTS_PAGE_VIEW_NAME));

        verify(service, times(1)).getAllClients();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testSetClientDiscountShouldThrowExceptionWhenInvalidDiscountSupplied() throws Exception{
        mockMvc.perform(post(SET_DISCOUNT_REQUEST)
                .param(USER_ID_PARAMETER, String.valueOf(USER_ID))
                .param(DISCOUNT_PARAMETER, String.valueOf(INVALID_DISCOUNT)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        verify(validator, times(1)).isDiscountValid(INVALID_DISCOUNT);
        verifyNoMoreInteractions(validator);
    }

    @Test
    public void testSetClientDiscountWhenValidDiscountSupplied() throws Exception{
        mockMvc.perform(post(SET_DISCOUNT_REQUEST)
                .param(USER_ID_PARAMETER, String.valueOf(USER_ID))
                .param(DISCOUNT_PARAMETER, String.valueOf(VALID_DISCOUNT)))
                .andExpect(redirectedUrl(CLIENTS_PAGE_URL));

        verify(validator, times(1)).isDiscountValid(VALID_DISCOUNT);
        verify(service, times(1)).setUserDiscount(USER_ID, VALID_DISCOUNT);
        verifyNoMoreInteractions(service, validator);
    }
}