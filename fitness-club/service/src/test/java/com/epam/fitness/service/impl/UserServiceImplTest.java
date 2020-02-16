package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.EntityNotFoundException;
import com.epam.fitness.exception.ServiceException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
@Ignore
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
        when(userDao.findById(EXISTENT_TRAINER_ID)).thenReturn(Optional.of(TRAINER));
        when(userDao.findById(NONEXISTENT_TRAINER_ID)).thenReturn(Optional.empty());
        when(userMapper.mapToDto(CLIENTS)).thenReturn(EXPECTED_CLIENTS_DTO);
        when(userDao.findClientsOfTrainer(TRAINER)).thenReturn(CLIENTS);

        when(userDao.getAllClients()).thenReturn(CLIENTS);

        when(userDao.findById(EXISTENT_USER_ID)).thenReturn(Optional.of(USER));
        when(userDao.findById(NONEXISTENT_USER_ID)).thenReturn(Optional.empty());
        when(userDao.save(USER)).thenReturn(USER);
        when(userDao.findUserByEmail(EXISTENT_EMAIL)).thenReturn(Optional.of(USER));
        when(userDao.findUserByEmail(NONEXISTENT_EMAIL)).thenReturn(Optional.empty());
        when(userMapper.mapToDto(USER)).thenReturn(USER_DTO);
        when(userMapper.mapToEntity(USER_DTO)).thenReturn(USER);
    }

    @Test
    public void testFindUserByEmailShouldReturnUserDtoWhenUserWithSuppliedEmailExists() throws ServiceException{
        //given

        //when
        UserDto actual = service.getUserByEmail(EXISTENT_EMAIL);

        //then
        Assert.assertEquals(USER_DTO, actual);

        verify(userDao, times(1)).findUserByEmail(EXISTENT_EMAIL);
        verify(userMapper, times(1)).mapToDto(USER);
        verifyNoMoreInteractions(userDao, userDao);
    }

    @Test(expected = ServiceException.class)
    public void testFindUserByEmailShouldThrowExceptionWhenUserWithSuppliedEmailDoesNotExist() throws ServiceException{
        //given

        //when
        service.getUserByEmail(NONEXISTENT_EMAIL);

        //then
        verify(userDao, times(1)).findUserByEmail(NONEXISTENT_EMAIL);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testGetAllClients() throws ServiceException{
        //given

        //when
        List<UserDto> actual = service.getAllClients();

        //then
        assertThat(actual, is(equalTo(EXPECTED_CLIENTS_DTO)));

        verify(userDao, times(1)).getAllClients();
        verify(userMapper, times(1)).mapToDto(CLIENTS);
        verifyNoMoreInteractions(userDao, userMapper);
    }

    @Test
    public void testGetClientsByTrainerIdShouldReturnListOfClientsWhenExistentTrainerIdSupplied() throws ServiceException{
        //given

        //when
        List<UserDto> actual = service.getClientsByTrainerId(EXISTENT_TRAINER_ID);

        //then
        assertThat(actual, is(equalTo(EXPECTED_CLIENTS_DTO)));

        verify(userDao, times(1)).findClientsOfTrainer(TRAINER);
        verify(userDao, times(1)).findById(EXISTENT_TRAINER_ID);
        verify(userMapper, times(1)).mapToDto(CLIENTS);
        verifyNoMoreInteractions(userDao, userMapper);
    }

    @Test(expected = ServiceException.class)
    public void testGetClientsByTrainerIdShouldThrowExceptionWhenNonexistentTrainerIdSupplied() throws ServiceException{
        //given

        //when
        service.getClientsByTrainerId(NONEXISTENT_TRAINER_ID);

        //then
        verify(userDao, times(1)).findById(NONEXISTENT_TRAINER_ID);
        verifyNoMoreInteractions(userDao, userMapper);
    }

    @Test
    public void testUpdateByIdShouldUpdateUserWhenExistentUserIdSupplied() throws ServiceException {
        //given

        //when
        UserDto actual = service.updateById(EXISTENT_USER_ID, USER_DTO);

        //then
        Assert.assertEquals(USER_DTO, actual);

        verify(userDao, times(1)).findById(EXISTENT_USER_ID);
        verify(userDao, times(1)).save(USER);
        verify(userMapper, times(1)).mapToEntity(USER_DTO);
        verify(userMapper, times(1)).mapToDto(USER);
        verifyNoMoreInteractions(userDao, userMapper);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateByIdShouldThrowExceptionWhenNonexistentUserIdSupplied() throws ServiceException {
        //given

        //when
        service.updateById(NONEXISTENT_USER_ID, USER_DTO);

        //then
        verify(userDao, times(1)).findById(NONEXISTENT_USER_ID);
        verifyNoMoreInteractions(userDao, userMapper);
    }

}