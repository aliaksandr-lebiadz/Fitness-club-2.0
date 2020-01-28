package com.epam.fitness.controller;

import com.epam.fitness.config.SpringWebMvcConfig;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.controller.ControllerAdviceImpl;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.validator.api.PaymentValidator;
import org.junit.After;
import org.junit.Before;
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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringWebMvcConfig.class)
public class PaymentControllerTest{

    private static final String GET_MEMBERSHIP_REQUEST = "/payment/getMembership";
    private static final String VALID_CARD_NUMBER = "4111111111111111";
    private static final String VALID_EXPIRATION_DATE = "11/20";
    private static final String VALID_CVV = "123";
    private static final String INVALID_CARD_NUMBER = "12341234123412345";
    private static final String INVALID_EXPIRATION_DATE = "07/19";
    private static final String INVALID_CVV = "12s";
    private static final User CLIENT = new User(5, "user", "pass", UserRole.CLIENT,
            "Mikhail", "Opel", 5);
    private static final String ORDERS_PAGE_URL = "/order/list";
    private static final String ERROR_PAGE_URL = "/error";
    private static final int MEMBERSHIP_ID = 58;
    private static final String CARD_NUMBER_PARAMETER = "card_number";
    private static final String VALID_THRU_PARAMETER = "valid_thru";
    private static final String CVV_PARAMETER = "cvv";
    private static final String MEMBERSHIP_SELECT_PARAMETER = "membership_select";

    private MockMvc mockMvc;

    @Mock
    private OrderService service;
    @Mock
    private PaymentValidator validator;
    @Mock
    private ControllerUtils utils;
    @InjectMocks
    private PaymentController paymentController;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(paymentController)
                .setControllerAdvice(new ControllerAdviceImpl())
                .build();
    }

    @Before
    public void createMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        when(utils.getCurrentUser()).thenReturn(Optional.of(CLIENT));
        when(validator.isCardNumberValid(VALID_CARD_NUMBER)).thenReturn(true);
        when(validator.isExpirationDateValid(VALID_EXPIRATION_DATE)).thenReturn(true);
        when(validator.isCvvValid(VALID_CVV)).thenReturn(true);
        when(validator.isCardNumberValid(INVALID_CARD_NUMBER)).thenReturn(false);
        when(validator.isExpirationDateValid(INVALID_EXPIRATION_DATE)).thenReturn(false);
        when(validator.isCvvValid(INVALID_CVV)).thenReturn(false);
        doNothing().when(service).create(CLIENT, MEMBERSHIP_ID);
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void testGetMembershipWhenValidParametersSuppliedAndUserIsAuthorizedAsClient() throws Exception{
        //given

        //when
        mockMvc.perform(post(GET_MEMBERSHIP_REQUEST)
                .param(CARD_NUMBER_PARAMETER, VALID_CARD_NUMBER)
                .param(VALID_THRU_PARAMETER, VALID_EXPIRATION_DATE)
                .param(CVV_PARAMETER, VALID_CVV)
                .param(MEMBERSHIP_SELECT_PARAMETER, String.valueOf(MEMBERSHIP_ID)))
                .andExpect(redirectedUrl(ORDERS_PAGE_URL));

        //then
        verify(validator, times(1)).isCardNumberValid(VALID_CARD_NUMBER);
        verify(validator, times(1)).isExpirationDateValid(VALID_EXPIRATION_DATE);
        verify(validator, times(1)).isCvvValid(VALID_CVV);
        verify(service, times(1)).create(CLIENT, MEMBERSHIP_ID);
        verify(utils, times(1)).getCurrentUser();
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void testGetMembershipShouldRedirectOnErrorPageWhenInvalidParametersSuppliedAndUserIsAuthorizedAsClient() throws Exception{
        //given

        //when
        mockMvc.perform(post(GET_MEMBERSHIP_REQUEST)
                .param(CARD_NUMBER_PARAMETER, INVALID_CARD_NUMBER)
                .param(VALID_THRU_PARAMETER, INVALID_EXPIRATION_DATE)
                .param(CVV_PARAMETER, INVALID_CVV)
                .param(MEMBERSHIP_SELECT_PARAMETER, String.valueOf(MEMBERSHIP_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
        verify(validator, times(1)).isCardNumberValid(INVALID_CARD_NUMBER);
        verify(validator, times(0)).isExpirationDateValid(INVALID_EXPIRATION_DATE);
        verify(validator, times(0)).isCvvValid(INVALID_CVV);
    }

    @Test
    @WithMockUser(authorities = { "TRAINER", "ADMIN" } )
    public void testGetMembershipShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsClient() throws Exception{
        //given

        //when
        mockMvc.perform(post(GET_MEMBERSHIP_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(service, validator, utils);
        reset(service, validator, utils);
    }

}