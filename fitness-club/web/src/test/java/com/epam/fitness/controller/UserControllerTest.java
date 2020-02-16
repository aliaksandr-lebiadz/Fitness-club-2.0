package com.epam.fitness.controller;

import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.DtoMappingException;
import com.epam.fitness.exception.EntityAlreadyExistsException;
import com.epam.fitness.exception.EntityNotFoundException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.controller.ControllerAdviceImpl;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.service.api.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest{

    private static final String USERS_URL = "/users";
    private static final String USER_BY_ID_URL = "/users/{id}";
    private static final String CLIENTS_URL = "/users/clients";
    private static final String CLIENTS_BY_USER_ID_URL = "/users/{id}/clients";
    private static final String ORDERS_BY_USER_ID_URL = "/users/{id}/orders";
    private static final int EXISTENT_USER_ID = 5;
    private static final int NONEXISTENT_USER_ID = 16;
    private static final UserDto USER_DTO = new UserDto(EXISTENT_USER_ID, "admin@mail.ru", "admin", UserRole.ADMIN,
            "Alex", "Lopov", 52);
    private static final UserDto EXISTENT_USER_DTO = new UserDto(3, "client@mail.ru", "client", UserRole.CLIENT,
            "Oleg", "Ilyasov", 11);
    private static final List<UserDto> USERS_DTO = Arrays.asList(
            new UserDto(1, 52),
            new UserDto(3, 1),
            new UserDto(6, 56)
    );

    private static final String FIRST_NAME_PARAMETER = "first_name";
    private static final String EMAIL_PARAMETER = "email";
    private static final String TRAINER_ID_PARAMETER = "trainer_id";
    private static final String SEARCH_FIRST_NAME = "Alex";
    private static final String SEARCH_EMAIL = "Alex@gmail.com";

    private static final List<OrderDto> ORDERS_DTO = Arrays.asList(
            new OrderDto(10, NutritionType.MEDIUM_CALORIE),
            new OrderDto(5, NutritionType.HIGH_CALORIE),
            new OrderDto(3, NutritionType.LOW_CALORIE)
    );
    private static final String GYM_MEMBERSHIP_ID_PARAMETER = "gym_membership_id";
    private static final int EXISTENT_GYM_MEMBERSHIP_ID = 11;
    private static final int NONEXISTENT_GYM_MEMBERSHIP_ID = 2;
    private static final OrderDto ORDER_DTO = new OrderDto(5, "my feedback...");

    @Mock
    private UserService userService;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private UserController controller;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdviceImpl())
                .build();
    }

    @Before
    public void createMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        when(userService.getById(EXISTENT_USER_ID)).thenReturn(USER_DTO);
        when(userService.getById(NONEXISTENT_USER_ID)).thenThrow(EntityNotFoundException.class);

        doNothing().when(userService).deleteById(EXISTENT_USER_ID);
        doThrow(EntityNotFoundException.class).when(userService).deleteById(NONEXISTENT_USER_ID);

        when(userService.updateById(EXISTENT_USER_ID, USER_DTO)).thenReturn(USER_DTO);
        when(userService.updateById(NONEXISTENT_USER_ID, USER_DTO)).thenThrow(EntityNotFoundException.class);
        when(userService.updateById(EXISTENT_USER_ID, null)).thenThrow(DtoMappingException.class);

        when(userService.create(USER_DTO)).thenReturn(USER_DTO);
        when(userService.create(EXISTENT_USER_DTO)).thenThrow(EntityAlreadyExistsException.class);

        when(userService.getAllClients()).thenReturn(USERS_DTO);

        when(userService.getClientsByTrainerId(EXISTENT_USER_ID)).thenReturn(USERS_DTO);
        when(userService.getClientsByTrainerId(NONEXISTENT_USER_ID)).thenThrow(EntityNotFoundException.class);

        when(userService.searchUsersByParameters(SEARCH_FIRST_NAME, null, SEARCH_EMAIL, null))
                .thenReturn(USERS_DTO);

        when(orderService.getOrdersByClientId(EXISTENT_USER_ID)).thenReturn(ORDERS_DTO);
        when(orderService.getOrdersByClientId(NONEXISTENT_USER_ID)).thenThrow(EntityNotFoundException.class);

        when(orderService.getOrdersOfTrainerClient(EXISTENT_USER_ID, EXISTENT_USER_ID)).thenReturn(ORDERS_DTO);
        when(orderService.getOrdersOfTrainerClient(EXISTENT_USER_ID, NONEXISTENT_USER_ID)).thenThrow(EntityNotFoundException.class);

        when(orderService.create(EXISTENT_USER_ID, EXISTENT_GYM_MEMBERSHIP_ID)).thenReturn(ORDER_DTO);
        when(orderService.create(NONEXISTENT_USER_ID, EXISTENT_GYM_MEMBERSHIP_ID)).thenThrow(EntityNotFoundException.class);
        when(orderService.create(EXISTENT_USER_ID, NONEXISTENT_GYM_MEMBERSHIP_ID)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    public void testGetUserByIdShouldReturnUserWhenExistentIdSupplied() throws Exception{
        //given

        //when
        String actualJson = mockMvc
                .perform(get(USER_BY_ID_URL, EXISTENT_USER_ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserDto actual = mapFromJson(actualJson, UserDto.class);

        //then
        Assert.assertEquals(USER_DTO, actual);
        verify(userService, times(1)).getById(EXISTENT_USER_ID);
    }

    @Test
    public void testGetUserByIdShouldReturnNotFoundStatusWhenNonexistentIdSupplied() throws Exception{
        //given

        //when
        mockMvc
                .perform(get(USER_BY_ID_URL, NONEXISTENT_USER_ID))
                .andExpect(status().isNotFound());

        //then
        verify(userService, times(1)).getById(NONEXISTENT_USER_ID);
    }

    @Test
    public void testDeleteUserByIdShouldReturnUserWhenExistentIdSupplied() throws Exception{
        //given

        //when
        mockMvc
                .perform(delete(USER_BY_ID_URL, EXISTENT_USER_ID))
                .andExpect(status().isNoContent());

        //then
        verify(userService, times(1)).deleteById(EXISTENT_USER_ID);
    }

    @Test
    public void testDeleteUserByIdShouldReturnNotFoundStatusWhenNonexistentIdSupplied() throws Exception{
        //given

        //when
        mockMvc
                .perform(delete(USER_BY_ID_URL, NONEXISTENT_USER_ID))
                .andExpect(status().isNotFound());

        //then
        verify(userService, times(1)).deleteById(NONEXISTENT_USER_ID);
    }

    @Test
    public void testUpdateUserByIdShouldReturnUpdatedUserWhenExistentIdSupplied() throws Exception{
        //given
        String requestBody = mapToJson(USER_DTO);

        //when
        String actualJson = mockMvc
                .perform(put(USER_BY_ID_URL, EXISTENT_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto actual = mapFromJson(actualJson, UserDto.class);

        //then
        Assert.assertEquals(USER_DTO, actual);

        verify(userService, times(1)).updateById(EXISTENT_USER_ID, USER_DTO);
    }

    @Test
    public void testUpdateUserByIdShouldReturnNotFoundStatusWhenNonexistentIdSupplied() throws Exception{
        //given
        String requestBody = mapToJson(USER_DTO);

        //when
        mockMvc
                .perform(put(USER_BY_ID_URL, NONEXISTENT_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());

        //then
        verify(userService, times(1)).updateById(NONEXISTENT_USER_ID, USER_DTO);
    }

    @Test
    public void testUpdateUserByIdShouldReturnBadRequestWhenNullRequestBodySupplied() throws Exception{
        //given
        String requestBody = mapToJson(USER_DTO);

        //when
        mockMvc
                .perform(put(USER_BY_ID_URL, NONEXISTENT_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());

        //then
        verify(userService, times(1)).updateById(NONEXISTENT_USER_ID, USER_DTO);
    }

    @Test
    public void testCreateUserShouldReturnCreatedUserWhenUserDoesNotExist() throws Exception{
        //given
        String requestBody = mapToJson(USER_DTO);

        //when
        String actualJson = mockMvc
                .perform(post(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto actual = mapFromJson(actualJson, UserDto.class);

        //then
        Assert.assertEquals(USER_DTO, actual);

        verify(userService, times(1)).create(USER_DTO);
    }

    @Test
    public void testCreateUserShouldReturnConflictStatusWhenUserAlreadyExists() throws Exception{
        //given
        String requestBody = mapToJson(EXISTENT_USER_DTO);

        //when
        mockMvc
                .perform(post(USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict());

        //then
        verify(userService, times(1)).create(EXISTENT_USER_DTO);
    }

    @Test
    public void testGetClientsShouldReturnAllClients() throws Exception {
        //given

        //when
        String actualJson = mockMvc
                .perform(get(CLIENTS_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto[] usersDto = mapFromJson(actualJson, UserDto[].class);
        List<UserDto> actual = Arrays.asList(usersDto);

        //then
        assertThat(actual, is(equalTo(USERS_DTO)));

        verify(userService, times(1)).getAllClients();
    }

    @Test
    public void testGetClientsByTrainerIdShouldReturnClientsOfTrainerWhenExistentIdSupplied() throws Exception {
        //given

        //when
        String actualJson = mockMvc
                .perform(get(CLIENTS_BY_USER_ID_URL, EXISTENT_USER_ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto[] usersDto = mapFromJson(actualJson, UserDto[].class);
        List<UserDto> actual = Arrays.asList(usersDto);

        //then
        assertThat(actual, is(equalTo(USERS_DTO)));

        verify(userService, times(1)).getClientsByTrainerId(EXISTENT_USER_ID);
    }

    @Test
    public void testGetClientsByTrainerIdShouldReturnNotFoundStatusWhenNonexistentIdSupplied() throws Exception {
        //given

        //when
        mockMvc
                .perform(get(CLIENTS_BY_USER_ID_URL, NONEXISTENT_USER_ID))
                .andExpect(status().isNotFound());

        //then
        verify(userService, times(1)).getClientsByTrainerId(NONEXISTENT_USER_ID);
    }

    @Test
    public void testGetUsersShouldReturnFoundUsers() throws Exception {
        //given

        //when
        String actualJson = mockMvc
                .perform(get(USERS_URL)
                        .param(FIRST_NAME_PARAMETER, SEARCH_FIRST_NAME)
                        .param(EMAIL_PARAMETER, SEARCH_EMAIL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto[] usersDto = mapFromJson(actualJson, UserDto[].class);
        List<UserDto> actual = Arrays.asList(usersDto);

        //then
        assertThat(actual, is(equalTo(USERS_DTO)));

        verify(userService, times(1))
                .searchUsersByParameters(SEARCH_FIRST_NAME, null, SEARCH_EMAIL, null);
    }

    @Test
    public void testGetOrderByClientIdShouldReturnOrdersOfClientWhenOnlyExistentIdSupplied() throws Exception {
        //given

        //when
        String actualJson = mockMvc
                .perform(get(ORDERS_BY_USER_ID_URL, EXISTENT_USER_ID))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderDto[] ordersDto = mapFromJson(actualJson, OrderDto[].class);
        List<OrderDto> actual = Arrays.asList(ordersDto);

        //then
        assertThat(actual, is(equalTo(ORDERS_DTO)));

        verify(orderService, times(1)).getOrdersByClientId(EXISTENT_USER_ID);
    }

    @Test
    public void testGetOrderByClientIdShouldReturnNotFoundStatusWhenOnlyNonexistentIdSupplied() throws Exception {
        //given

        //when
        mockMvc
                .perform(get(ORDERS_BY_USER_ID_URL, NONEXISTENT_USER_ID))
                .andExpect(status().isNotFound());

        //then
        verify(orderService, times(1)).getOrdersByClientId(NONEXISTENT_USER_ID);
    }

    @Test
    public void testGetOrderByClientIdShouldReturnOrdersOfTrainerClientWhenExistentTrainerIdSupplied() throws Exception {
        //given

        //when
        String actualJson = mockMvc
                .perform(get(ORDERS_BY_USER_ID_URL, EXISTENT_USER_ID)
                        .param(TRAINER_ID_PARAMETER, String.valueOf(EXISTENT_USER_ID)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderDto[] ordersDto = mapFromJson(actualJson, OrderDto[].class);
        List<OrderDto> actual = Arrays.asList(ordersDto);

        //then
        assertThat(actual, is(equalTo(ORDERS_DTO)));

        verify(orderService, times(1)).getOrdersOfTrainerClient(EXISTENT_USER_ID, EXISTENT_USER_ID);
    }

    @Test
    public void testGetOrderByClientIdShouldReturnNotFoundStatusWhenNonexistentTrainerIdSupplied() throws Exception {
        //given

        //when
        mockMvc
                .perform(get(ORDERS_BY_USER_ID_URL, EXISTENT_USER_ID)
                        .param(TRAINER_ID_PARAMETER, String.valueOf(NONEXISTENT_USER_ID)))
                .andExpect(status().isNotFound());

        //then
        verify(orderService, times(1)).getOrdersOfTrainerClient(EXISTENT_USER_ID, NONEXISTENT_USER_ID);
    }

    @Test
    public void testCreateOrderShouldReturnCreatedOrderWhenExistentClientIdAndGymMembershipIdSupplied() throws Exception {
        //given

        //when
        String actualJson = mockMvc
                .perform(post(ORDERS_BY_USER_ID_URL, EXISTENT_USER_ID)
                        .param(GYM_MEMBERSHIP_ID_PARAMETER, String.valueOf(EXISTENT_GYM_MEMBERSHIP_ID)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        OrderDto actual = mapFromJson(actualJson, OrderDto.class);

        //then
        Assert.assertEquals(ORDER_DTO, actual);

        verify(orderService, times(1)).create(EXISTENT_USER_ID, EXISTENT_GYM_MEMBERSHIP_ID);
    }

    @Test
    public void testCreateOrderShouldReturnNotFoundStatusWhenNonexistentClientIdSupplied() throws Exception {
        //given

        //when
        mockMvc
                .perform(post(ORDERS_BY_USER_ID_URL, NONEXISTENT_USER_ID)
                        .param(GYM_MEMBERSHIP_ID_PARAMETER, String.valueOf(EXISTENT_GYM_MEMBERSHIP_ID)))
                .andExpect(status().isNotFound());

        //then
        verify(orderService, times(1)).create(NONEXISTENT_USER_ID, EXISTENT_GYM_MEMBERSHIP_ID);
    }

    @Test
    public void testCreateOrderShouldReturnNotFoundStatusWhenNonexistentGymMembershipIdSupplied() throws Exception {
        //given

        //when
        mockMvc
                .perform(post(ORDERS_BY_USER_ID_URL, EXISTENT_USER_ID)
                        .param(GYM_MEMBERSHIP_ID_PARAMETER, String.valueOf(NONEXISTENT_GYM_MEMBERSHIP_ID)))
                .andExpect(status().isNotFound());

        //then
        verify(orderService, times(1)).create(EXISTENT_USER_ID, NONEXISTENT_GYM_MEMBERSHIP_ID);
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(userService, orderService);
        reset(userService, orderService);
    }
}