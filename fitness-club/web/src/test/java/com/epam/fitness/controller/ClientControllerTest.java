package com.epam.fitness.controller;

import com.epam.fitness.config.SpringWebMvcConfig;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import com.epam.fitness.validator.api.UserValidator;
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

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringWebMvcConfig.class)
public class ClientControllerTest{

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

    private MockMvc mockMvc;

    @Mock
    private UserService service;
    @Mock
    private UserValidator validator;
    @InjectMocks
    private ClientController clientController;


    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(clientController)
                .setControllerAdvice(new ControllerAdviceImpl())
                .build();
    }

    @Before
    public void createMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        when(service.getAllClients()).thenReturn(EXPECTED_CLIENTS);
        doNothing().when(service).setUserDiscount(anyInt(), anyInt());
        when(validator.isDiscountValid(VALID_DISCOUNT)).thenReturn(true);
        when(validator.isDiscountValid(INVALID_DISCOUNT)).thenReturn(false);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testGetClientsPageWhenUserIsAuthorizedAsAdmin() throws Exception{
        //given

        //when
        mockMvc.perform(get(CLIENTS_PAGE_REQUEST))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists(CLIENTS_ATTRIBUTE))
                .andExpect(model().attribute(CLIENTS_ATTRIBUTE, EXPECTED_CLIENTS))
                .andExpect(view().name(CLIENTS_PAGE_VIEW_NAME));

        //then
        verify(service, times(1)).getAllClients();
        verifyNoMoreInteractions(service);
    }

    @Ignore
    @Test
    @WithMockUser(authorities = { "CLIENT", "TRAINER"} )
    public void testGetClientsShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsAdmin() throws Exception{
        //given

        //when
        mockMvc.perform(get(CLIENTS_PAGE_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));
                //.andExpect(view().name("clients"));

        //then
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testSetClientDiscountShouldThrowExceptionWhenInvalidDiscountSupplied() throws Exception{
        //given

        //when
        mockMvc.perform(post(SET_DISCOUNT_REQUEST)
                .param(USER_ID_PARAMETER, String.valueOf(USER_ID))
                .param(DISCOUNT_PARAMETER, String.valueOf(INVALID_DISCOUNT)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
        verify(validator, times(1)).isDiscountValid(INVALID_DISCOUNT);
        verifyNoMoreInteractions(validator);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testSetClientDiscountWhenValidDiscountSuppliedAndUserIsAuthorizedAsAdmin() throws Exception{
        //given

        //when
        mockMvc.perform(post(SET_DISCOUNT_REQUEST)
                .param(USER_ID_PARAMETER, String.valueOf(USER_ID))
                .param(DISCOUNT_PARAMETER, String.valueOf(VALID_DISCOUNT)))
                .andExpect(redirectedUrl(CLIENTS_PAGE_URL));

        //then
        verify(validator, times(1)).isDiscountValid(VALID_DISCOUNT);
        verify(service, times(1)).setUserDiscount(USER_ID, VALID_DISCOUNT);
        verifyNoMoreInteractions(service, validator);
    }

    @Test
    @WithMockUser(authorities = { "CLIENT", "TRAINER" })
    public void testSetClientDiscountShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsAdmin() throws Exception{
        //given

        //when
        mockMvc.perform(post(SET_DISCOUNT_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }
}