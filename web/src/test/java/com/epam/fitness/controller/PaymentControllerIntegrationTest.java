package com.epam.fitness.controller;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(DataProviderRunner.class)
public class PaymentControllerIntegrationTest extends AbstractIntegrationTest {


    private static final String GET_MEMBERSHIP_REQUEST = "/payment/getMembership";
    private static final String VALID_CARD_NUMBER = "4111111111111111";
    private static final String VALID_EXPIRATION_DATE = "11/21";
    private static final String VALID_CVV = "123";
    private static final int CLIENT_ID = 3;
    private static final User CLIENT = new User(CLIENT_ID, "client@gmail.co,", "pass", UserRole.CLIENT,
            "Mikhail", "Opel", 5);
    private static final User RANDOM_TRAINER = new User(3, "trainer@gmail.com", "pass", UserRole.TRAINER, "trainer", "trainer", 0);
    private static final String ORDERS_PAGE_URL = "/order/list";
    private static final String ERROR_PAGE_URL = "/error";
    private static final int EXISTENT_GYM_MEMBERSHIP_ID = 58;
    private static final int NONEXISTENT_GYM_MEMBERSHIP_ID = 3;
    private static final GymMembership GYM_MEMBERSHIP = new GymMembership(EXISTENT_GYM_MEMBERSHIP_ID, 6, BigDecimal.TEN);
    private static final String CARD_NUMBER_PARAMETER = "card_number";
    private static final String VALID_THRU_PARAMETER = "valid_thru";
    private static final String CVV_PARAMETER = "cvv";
    private static final String MEMBERSHIP_SELECT_PARAMETER = "membership_select";

    @DataProvider
    public static Object[][] invalidCardNumbersDataProvider() {
        return new Object[][] {
                { "123412341234123" }, // < 16 symbols
                { "12341234123412345" }, // > 16 symbols
                { "1111111111111111" }, // invalid format
                { "6011000000000003" }, // invalid format
        };
    }

    @DataProvider
    public static Object[][] invalidExpirationDatesDataProvider() {
        return new Object[][] {
                { "11.25" }, // invalid format
                { "05/11/25" }, // invalid format
                { "12.12.25" }, // invalid format
                { "11/19" }, // past
        };
    }

    @DataProvider
    public static Object[][] invalidCvvsDataProvider() {
        return new Object[][] {
                { "12" }, // < 3 symbols
                { "9852" }, // > 3 symbols
                { "1s2" }, // invalid format
        };
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private Dao<GymMembership> gymMembershipDao;

    @Autowired
    private OrderDao orderDao;

    @Before
    public void setUp(){
        when(userDao.findUserByEmail("client@gmail.com")).thenReturn(Optional.of(CLIENT));
        when(userDao.findById(CLIENT_ID)).thenReturn(Optional.of(CLIENT));
        when(userDao.getRandomTrainer()).thenReturn(RANDOM_TRAINER);
        when(gymMembershipDao.findById(EXISTENT_GYM_MEMBERSHIP_ID)).thenReturn(Optional.of(GYM_MEMBERSHIP));
        when(gymMembershipDao.findById(NONEXISTENT_GYM_MEMBERSHIP_ID)).thenReturn(Optional.empty());
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void getMembership_withTrainerUserRole_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(GET_MEMBERSHIP_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT", username = "client@gmail.com")
    @UseDataProvider("invalidCardNumbersDataProvider")
    public void getMembership_withInvalidCardNumber_redirectOnErrorPage(String cardNumber) throws Exception {
        //given

        //when
        mockMvc.perform(post(GET_MEMBERSHIP_REQUEST)
                .param(CARD_NUMBER_PARAMETER, cardNumber)
                .param(VALID_THRU_PARAMETER, VALID_EXPIRATION_DATE)
                .param(CVV_PARAMETER, VALID_CVV)
                .param(MEMBERSHIP_SELECT_PARAMETER, String.valueOf(EXISTENT_GYM_MEMBERSHIP_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT", username = "client@gmail.com")
    @UseDataProvider("invalidExpirationDatesDataProvider")
    public void getMembership_withInvalidExpirationDate_redirectOnErrorPage(String expirationDate) throws Exception {
        //given

        //when
        mockMvc.perform(post(GET_MEMBERSHIP_REQUEST)
                .param(CARD_NUMBER_PARAMETER, VALID_CARD_NUMBER)
                .param(VALID_THRU_PARAMETER, expirationDate)
                .param(CVV_PARAMETER, VALID_CVV)
                .param(MEMBERSHIP_SELECT_PARAMETER, String.valueOf(EXISTENT_GYM_MEMBERSHIP_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT", username = "client@gmail.com")
    @UseDataProvider("invalidCvvsDataProvider")
    public void getMembership_withInvalidCvv_redirectOnErrorPage(String cvv) throws Exception {
        //given

        //when
        mockMvc.perform(post(GET_MEMBERSHIP_REQUEST)
                .param(CARD_NUMBER_PARAMETER, VALID_CARD_NUMBER)
                .param(VALID_THRU_PARAMETER, VALID_EXPIRATION_DATE)
                .param(CVV_PARAMETER, cvv)
                .param(MEMBERSHIP_SELECT_PARAMETER, String.valueOf(EXISTENT_GYM_MEMBERSHIP_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT", username = "client@gmail.com")
    public void getMembership_withNonexistentMembershipId_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(GET_MEMBERSHIP_REQUEST)
                .param(CARD_NUMBER_PARAMETER, VALID_CARD_NUMBER)
                .param(VALID_THRU_PARAMETER, VALID_EXPIRATION_DATE)
                .param(CVV_PARAMETER, VALID_CVV)
                .param(MEMBERSHIP_SELECT_PARAMETER, String.valueOf(NONEXISTENT_GYM_MEMBERSHIP_ID)))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
        unwrapAndVerify(userDao, times(1)).findById(CLIENT_ID);
        unwrapAndVerify(userDao, times(1)).findUserByEmail("client@gmail.com");
        unwrapAndVerify(gymMembershipDao, times(1)).findById(NONEXISTENT_GYM_MEMBERSHIP_ID);
    }

    @Test
    @WithMockUser(authorities = "CLIENT", username = "client@gmail.com")
    public void getMembership_withExistentMembershipId_membershipSuccessfullyCreatedAndRedirectOnOrdersPage() throws Exception {
        //given
        when(orderDao.save(any(Order.class))).thenReturn(any(Order.class));

        //when
        mockMvc.perform(post(GET_MEMBERSHIP_REQUEST)
                .param(CARD_NUMBER_PARAMETER, VALID_CARD_NUMBER)
                .param(VALID_THRU_PARAMETER, VALID_EXPIRATION_DATE)
                .param(CVV_PARAMETER, VALID_CVV)
                .param(MEMBERSHIP_SELECT_PARAMETER, String.valueOf(EXISTENT_GYM_MEMBERSHIP_ID)))
                .andExpect(redirectedUrl(ORDERS_PAGE_URL));

        //then
        unwrapAndVerify(userDao, times(1)).findById(CLIENT_ID);
        unwrapAndVerify(userDao, times(1)).findUserByEmail("client@gmail.com");
        unwrapAndVerify(gymMembershipDao, times(1)).findById(EXISTENT_GYM_MEMBERSHIP_ID);
        unwrapAndVerify(userDao, times(1)).getRandomTrainer();
        unwrapAndVerify(orderDao, times(1)).save(any(Order.class));
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(userDao, gymMembershipDao, orderDao);
        reset(userDao, gymMembershipDao, orderDao);
    }

}