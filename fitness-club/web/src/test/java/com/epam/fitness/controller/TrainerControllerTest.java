package com.epam.fitness.controller;

import com.epam.fitness.config.SpringWebMvcConfig;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.ExerciseService;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.service.api.UserService;
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
public class TrainerControllerTest{

    private static final String TRAINER_CLIENTS_PAGE_REQUEST = "/trainer/clients";
    private static final String CLIENT_ID_PARAMETER = "client_id";
    private static final String TRAINER_CLIENTS_PAGE_VIEW_NAME = "trainer_clients";
    private static final int TRAINER_ID = 5;
    private static final int CLIENT_ID = 10;
    private static final User TRAINER = new User(TRAINER_ID, "trainer", "tr", UserRole.TRAINER,
            "Alex", "Lopez", 100);
    private static final List<Order> EXPECTED_ORDERS;
    private static final List<Exercise> EXPECTED_EXERCISES = Arrays.asList(
            new Exercise(1, "push-ups"),
            new Exercise(2, "squat"),
            new Exercise(3, "sit-ups"),
            new Exercise(4, "pull-ups"));
    private static final List<User> EXPECTED_CLIENTS = Arrays.asList(
            new User(5, "client@gmail.com", "client", UserRole.CLIENT, "Alex", "Kotov", 11),
            new User(11, "client2@gmail.com", "client", UserRole.CLIENT, "Mikhail", "Dubov", 1));
    private static final String ERROR_PAGE_URL = "/error";
    private static final String CLIENTS_ATTRIBUTE = "clients";

    static{
        Order.Builder builder = Order.createBuilder();
        EXPECTED_ORDERS = Arrays.asList(
                builder.setId(5).build(),
                builder.setId(6).build(),
                builder.setId(1).build(),
                builder.setId(8).build());
    }

    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private OrderService orderService;
    @Mock
    private ExerciseService exerciseService;
    @Mock
    private ControllerUtils utils;
    @InjectMocks
    private TrainerController trainerController;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(trainerController)
                .setControllerAdvice(new ControllerAdviceImpl())
                .build();
    }

    @Before
    public void createMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        when(orderService.getOrdersOfTrainerClient(CLIENT_ID, TRAINER)).thenReturn(EXPECTED_ORDERS);
        when(exerciseService.getAll()).thenReturn(EXPECTED_EXERCISES);
        when(userService.getClientsOfTrainer(TRAINER)).thenReturn(EXPECTED_CLIENTS);
        when(utils.getCurrentUser()).thenReturn(Optional.of(TRAINER));
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void testGetTrainerClientsPageWhenClientIdSuppliedAndUserIsAuthorizedAsTrainer() throws Exception{
        //given
        final String clientOrdersAttribute = "client_orders";
        final String exercisesAttribute = "exercises";

        //when
        mockMvc.perform(get(TRAINER_CLIENTS_PAGE_REQUEST)
                .param(CLIENT_ID_PARAMETER, String.valueOf(CLIENT_ID)))
                .andExpect(status().isOk())
                .andExpect(model().size(3))
                .andExpect(model().attributeExists(clientOrdersAttribute, exercisesAttribute, CLIENTS_ATTRIBUTE))
                .andExpect(model().attribute(clientOrdersAttribute, EXPECTED_ORDERS))
                .andExpect(model().attribute(exercisesAttribute, EXPECTED_EXERCISES))
                .andExpect(model().attribute(CLIENTS_ATTRIBUTE, EXPECTED_CLIENTS))
                .andExpect(view().name(TRAINER_CLIENTS_PAGE_VIEW_NAME));

        //then
        verify(orderService, times(1)).getOrdersOfTrainerClient(CLIENT_ID, TRAINER);
        verify(exerciseService, times(1)).getAll();
        verify(userService, times(1)).getClientsOfTrainer(TRAINER);
        verify(utils, times(1)).getCurrentUser();
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void testGetTrainerClientsPageWhenClientIdIsNotSuppliedAndUserIsAuthorizedAsTrainer() throws Exception{
        //given

        //when
        mockMvc.perform(get(TRAINER_CLIENTS_PAGE_REQUEST))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists(CLIENTS_ATTRIBUTE))
                .andExpect(model().attribute(CLIENTS_ATTRIBUTE, EXPECTED_CLIENTS))
                .andExpect(view().name(TRAINER_CLIENTS_PAGE_VIEW_NAME));

        //then
        verify(userService, times(1)).getClientsOfTrainer(TRAINER);
        verify(utils, times(1)).getCurrentUser();
    }

    @Ignore
    @Test
    @WithMockUser(authorities = { "CLIENT", "ADMIN"} )
    public void testGetTrainerClientsPageShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsTrainer() throws Exception{
        //given

        //when
        mockMvc.perform(get(TRAINER_CLIENTS_PAGE_REQUEST))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(orderService, exerciseService, userService, utils);
        reset(orderService, exerciseService, userService, utils);
    }

}