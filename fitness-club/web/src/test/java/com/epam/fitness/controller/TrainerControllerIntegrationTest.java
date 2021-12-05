package com.epam.fitness.controller;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class TrainerControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String TRAINER_URL = "/trainer";
    private static final String ERROR_URL = "/error";
    private static final String EMAIL = "admin@gmail.com";
    private static final String PASSWORD = "qwerty12345";
    private static final String FIRST_NAME = "Alex";
    private static final String SECOND_NAME = "Lopov";
    private static final int TRAINER_ID = 5;
    private static final int CLIENT_ID = 10;
    private static final User TRAINER = new User(TRAINER_ID, "trainer", "tr", UserRole.TRAINER,
            "Alex", "Lopez", 100);
    private static final Order ORDER = Order.createBuilder()
            .setNutritionType(NutritionType.HIGH_CALORIE)
            .setBeginDate(Date.valueOf("2019-05-12"))
            .setEndDate(Date.valueOf("2020-12-05"))
            .setPrice(BigDecimal.TEN)
            .build();
    private static final Exercise EXERCISE = new Exercise(5, "squat");
    private static final User CLIENT = new User(5, "client@gmail.com", "client", UserRole.CLIENT, "Alex", "Kotov", 11);

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private Dao<Exercise> exerciseDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){
        when(userDao.findUserByEmail("trainer@gmail.com")).thenReturn(Optional.of(TRAINER));
        when(userDao.findById(TRAINER_ID)).thenReturn(Optional.of(TRAINER));
        when(exerciseDao.getAll()).thenReturn(Collections.singletonList(EXERCISE));
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void addTrainer_withClientUserRole_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(TRAINER_URL + "/add")
                .param("email", EMAIL)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addTrainer_withInvalidEmail_redirectOnErrorPage() throws Exception {
        //given
        final String invalidEmail = "123";

        //when
        mockMvc.perform(post(TRAINER_URL + "/add")
                .param("email", invalidEmail)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addTrainer_withInvalidPasswordLength_redirectOnErrorPage() throws Exception {
        //given
        final String invalidPassword = "pass";

        //when
        mockMvc.perform(post(TRAINER_URL + "/add")
                .param("email", EMAIL)
                .param("password", invalidPassword)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addTrainer_withInvalidFirstNameLength_redirectOnErrorPage() throws Exception {
        //given
        final String invalidFirstName = "f";

        //when
        mockMvc.perform(post(TRAINER_URL + "/add")
                .param("email", EMAIL)
                .param("password", PASSWORD)
                .param("first_name", invalidFirstName)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addTrainer_withInvalidSecondNameLength_redirectOnErrorPage() throws Exception {
        //given
        final String invalidSecondName = "s";

        //when
        mockMvc.perform(post(TRAINER_URL + "/add")
                .param("email", EMAIL)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", invalidSecondName))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void trainer_withExistentEmail_redirectOnErrorPage() throws Exception {
        //given
        final String existentEmail = "client@mail.ru";
        when(userDao.findUserByEmail(existentEmail)).thenReturn(Optional.of(TRAINER));

        //when
        mockMvc.perform(post(TRAINER_URL + "/add")
                .param("email", existentEmail)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
        unwrapAndVerify(userDao, times(1)).findUserByEmail(existentEmail);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addUser_withNonexistentEmail_trainerSuccessfullyCreatedAndRedirectOnUsersPage() throws Exception {
        //given
        final String nonexistentEmail = "client1@mail.ru";
        final User user = new User(null, nonexistentEmail, PASSWORD, UserRole.TRAINER, FIRST_NAME, SECOND_NAME, 0);
        when(userDao.findUserByEmail(nonexistentEmail)).thenReturn(Optional.empty());
        when(userDao.save(user)).thenReturn(user);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

        //when
        mockMvc.perform(post(TRAINER_URL + "/add")
                .param("email", nonexistentEmail)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl("/user/list"));

        //then
        unwrapAndVerify(userDao, times(1)).findUserByEmail(nonexistentEmail);
        unwrapAndVerify(userDao, times(1)).save(user);
        unwrapAndVerify(passwordEncoder, times(1)).encode(PASSWORD);
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void getTrainerClientsPage_withClientUserRole_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get(TRAINER_URL + "/clients"))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER", username = "trainer@gmail.com")
    public void getTrainerClientsPage_withEmptyClientId_trainerClientsPageView() throws Exception {
        //given
        when(userDao.findClientsOfTrainer(any(User.class))).thenReturn(Collections.singletonList(CLIENT));

        //when
        mockMvc.perform(get(TRAINER_URL + "/clients"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("clients", hasSize(1)))
                .andExpect(model().attribute("clients", hasItem(allOf(
                        hasProperty("firstName", is(CLIENT.getFirstName())),
                        hasProperty("secondName", is(CLIENT.getSecondName())),
                        hasProperty("discount", is(CLIENT.getDiscount())),
                        hasProperty("id", is(CLIENT.getId())),
                        hasProperty("email", is(CLIENT.getEmail()))
                ))))
                .andExpect(view().name("trainer_clients"));

        //then
        unwrapAndVerify(userDao, times(1)).findUserByEmail("trainer@gmail.com");
        unwrapAndVerify(userDao, times(1)).findById(TRAINER_ID);
        unwrapAndVerify(userDao, times(1)).findClientsOfTrainer(TRAINER);
    }

    @Test
    @WithMockUser(authorities = "TRAINER", username = "trainer@gmail.com")
    public void getTrainerClientsPage_witNonEmptyClientId_trainerClientsPageViewWithOrdersAndExercises() throws Exception {
        //given
        when(userDao.findClientsOfTrainer(any(User.class))).thenReturn(Collections.singletonList(CLIENT));
        when(orderDao.findOrdersOfTrainerClient(eq(CLIENT_ID), any(User.class))).thenReturn(Collections.singletonList(ORDER));

        //when
        mockMvc.perform(get(TRAINER_URL + "/clients")
                .param("client_id", String.valueOf(CLIENT_ID)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("clients", hasSize(1)))
                .andExpect(model().attribute("clients", hasItem(allOf(
                        hasProperty("firstName", is(CLIENT.getFirstName())),
                        hasProperty("secondName", is(CLIENT.getSecondName())),
                        hasProperty("discount", is(CLIENT.getDiscount())),
                        hasProperty("id", is(CLIENT.getId())),
                        hasProperty("email", is(CLIENT.getEmail()))
                ))))
                .andExpect(model().attribute("client_orders", hasSize(1)))
                .andExpect(model().attribute("client_orders", hasItem(allOf(
                        hasProperty("price", is(ORDER.getPrice())),
                        hasProperty("beginDate", is(ORDER.getBeginDate())),
                        hasProperty("endDate", is(ORDER.getEndDate())),
                        hasProperty("nutritionType", is(ORDER.getNutritionType()))
                ))))
                .andExpect(model().attribute("exercises", hasSize(1)))
                .andExpect(model().attribute("exercises", hasItem(allOf(
                        hasProperty("id", is(EXERCISE.getId())),
                        hasProperty("name", is(EXERCISE.getName()))
                ))))
                .andExpect(view().name("trainer_clients"));

        //then
        unwrapAndVerify(userDao, times(1)).findUserByEmail("trainer@gmail.com");
        unwrapAndVerify(userDao, times(2)).findById(TRAINER_ID);
        unwrapAndVerify(userDao, times(1)).findClientsOfTrainer(TRAINER);
        unwrapAndVerify(orderDao, times(1)).findOrdersOfTrainerClient(eq(CLIENT_ID), any(User.class));
        unwrapAndVerify(exerciseDao, times(1)).getAll();
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(userDao, passwordEncoder, orderDao, exerciseDao);
        reset(userDao, passwordEncoder, orderDao, exerciseDao);
    }

}