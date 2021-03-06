package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.EntityNotFoundException;
import com.epam.fitness.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String EXISTENT_EMAIL = "admin@gmail.com";
    private static final String NONEXISTENT_EMAIL = "client@gmail.com";
    private static final int EXISTENT_USER_ID = 13;
    private static final int NONEXISTENT_USER_ID = 33;
    private static final User USER = new User(5, EXISTENT_EMAIL, "admin", UserRole.ADMIN,
            "Alex", "Tuhev", 15);
    private static final UserDto USER_DTO = new UserDto(5, EXISTENT_EMAIL, "admin", UserRole.ADMIN,
            "Alex", "Tuhev", 15);

    private static final List<User> CLIENTS = Collections.singletonList(USER);
    private static final List<UserDto> EXPECTED_CLIENTS_DTO = Collections.singletonList(USER_DTO);
    private static final User TRAINER = new User(3, "trainer@gmail.com", "trainer", UserRole.TRAINER,
            "Ivan", "Feoktistov", 10);
    private static final UserDto TRAINER_DTO = new UserDto(3, "trainer@gmail.com", "trainer", UserRole.TRAINER,
            "Ivan", "Feoktistov", 10);
    private static final int EXISTENT_TRAINER_ID = 3;
    private static final int NONEXISTENT_TRAINER_ID = 15;

    @Mock
    private UserDao userDao;
    @Mock
    private DtoMapper<User, UserDto> userMapper;
    @InjectMocks
    private UserServiceImpl service;

    @Before
    public void createMocks() throws ServiceException{
        MockitoAnnotations.initMocks(this);

        when(userDao.findById(EXISTENT_TRAINER_ID)).thenReturn(Optional.of(TRAINER));
        when(userDao.findById(NONEXISTENT_TRAINER_ID)).thenReturn(Optional.empty());
        when(userDao.findById(EXISTENT_USER_ID)).thenReturn(Optional.of(USER));
        when(userDao.findById(NONEXISTENT_USER_ID)).thenReturn(Optional.empty());
        when(userDao.findClientsOfTrainer(TRAINER)).thenReturn(CLIENTS);
        when(userDao.getAllClients()).thenReturn(CLIENTS);
        when(userDao.save(USER)).thenReturn(USER);
        when(userDao.findUserByEmail(EXISTENT_EMAIL)).thenReturn(Optional.of(USER));
        when(userDao.findUserByEmail(NONEXISTENT_EMAIL)).thenReturn(Optional.empty());

        when(userMapper.mapToDto(CLIENTS)).thenReturn(EXPECTED_CLIENTS_DTO);
        when(userMapper.mapToDto(USER)).thenReturn(USER_DTO);
        when(userMapper.mapToEntity(USER_DTO)).thenReturn(USER);
    }

    @Test
    public void getUserByEmailWhenUserWithSuppliedEmailExists() throws ServiceException{
        //given

        //when
        UserDto actual = service.getUserByEmail(EXISTENT_EMAIL);

        //then
        assertEquals(USER_DTO, actual);

        verify(userDao).findUserByEmail(EXISTENT_EMAIL);
        verify(userMapper).mapToDto(USER);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getUserByEmailWithEntityNotFoundException() throws ServiceException{
        //given

        //when/then
        try{
            service.getUserByEmail(NONEXISTENT_EMAIL);
        } finally {
            verify(userDao).findUserByEmail(NONEXISTENT_EMAIL);
        }
    }

    @Test
    public void getAllClients() throws ServiceException{
        //given

        //when
        List<UserDto> actual = service.getAllClients();

        //then
        assertThat(actual, is(equalTo(EXPECTED_CLIENTS_DTO)));

        verify(userDao).getAllClients();
        verify(userMapper).mapToDto(CLIENTS);
    }

    @Test
    public void getClientsByTrainerIdWhenExistentTrainerIdSupplied() throws ServiceException{
        //given

        //when
        List<UserDto> actual = service.getClientsByTrainerId(EXISTENT_TRAINER_ID);

        //then
        assertThat(actual, is(equalTo(EXPECTED_CLIENTS_DTO)));

        verify(userDao).findClientsOfTrainer(TRAINER);
        verify(userDao).findById(EXISTENT_TRAINER_ID);
        verify(userMapper).mapToDto(CLIENTS);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getClientsByTrainerIdWithEntityNotFoundException() throws ServiceException{
        //given

        //when/then
        try{
            service.getClientsByTrainerId(NONEXISTENT_TRAINER_ID);
        } finally {
            verify(userDao).findById(NONEXISTENT_TRAINER_ID);
        }
    }

    @Test
    public void updateByIdWhenExistentUserIdSupplied() throws ServiceException {
        //given

        //when
        UserDto actual = service.updateById(EXISTENT_USER_ID, USER_DTO);

        //then
        assertEquals(USER_DTO, actual);

        verify(userDao).findById(EXISTENT_USER_ID);
        verify(userDao).save(USER);
        verify(userMapper).mapToEntity(USER_DTO);
        verify(userMapper).mapToDto(USER);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateByIdWithEntityNotFoundException() throws ServiceException {
        //given

        //when/then
        try{
            service.updateById(NONEXISTENT_USER_ID, USER_DTO);
        } finally {
            verify(userMapper).mapToEntity(USER_DTO);
            verify(userDao).findById(NONEXISTENT_USER_ID);
        }
    }

    @After
    public void verifyMocks() {
        verifyNoMoreInteractions(userDao, userMapper);
        reset(userDao, userMapper);
    }

}