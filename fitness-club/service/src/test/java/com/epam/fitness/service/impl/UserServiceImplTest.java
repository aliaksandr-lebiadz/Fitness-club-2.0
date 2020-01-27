package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.EntityMappingException;
import com.epam.fitness.exception.ServiceException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String EXISTENT_EMAIL = "admin@gmail.com";
    private static final String NONEXISTENT_EMAIL = "client@gmail.com";
    private static final User USER = new User(5, EXISTENT_EMAIL, "admin", UserRole.ADMIN,
            "Alex", "Tuhev", 15);
    private static final UserDto USER_DTO_WITH_EXISTENT_ID = new UserDto(5, EXISTENT_EMAIL, "admin", UserRole.ADMIN,
            "Alex", "Tuhev", 15);
    private static final UserDto USER_DTO_WITH_NONEXISTENT_ID =
            new UserDto(103, "user@gmail.com", "user", UserRole.CLIENT,
                    "Mikhail", "Olev", 10);

    private static final List<User> CLIENTS = Collections.singletonList(USER);
    private static final List<UserDto> EXPECTED_CLIENTS_DTO = Collections.singletonList(USER_DTO_WITH_EXISTENT_ID);
    private static final User TRAINER = new User(3, "trainer@gmail.com", "trainer", UserRole.TRAINER,
            "Ivan", "Feoktistov", 10);
    private static final UserDto TRAINER_DTO = new UserDto(3, "trainer@gmail.com", "trainer", UserRole.TRAINER,
            "Ivan", "Feoktistov", 10);



    @Mock
    private UserDao userDao;
    @Mock
    private DtoMapper<User, UserDto> userDtoMapper;
    @InjectMocks
    private UserServiceImpl service;

    @Before
    public void createMocks() throws EntityMappingException {
        when(userDao.findUserByEmail(EXISTENT_EMAIL)).thenReturn(Optional.of(USER));
        when(userDao.findUserByEmail(NONEXISTENT_EMAIL)).thenReturn(Optional.empty());
        when(userDtoMapper.mapToDto(USER)).thenReturn(USER_DTO_WITH_EXISTENT_ID);
        when(userDtoMapper.mapToEntity(USER_DTO_WITH_EXISTENT_ID)).thenReturn(USER);

        when(userDao.getAllClients()).thenReturn(CLIENTS);

        when(userDtoMapper.mapToEntity(TRAINER_DTO)).thenReturn(TRAINER);
        when(userDao.findClientsOfTrainer(TRAINER)).thenReturn(CLIENTS);

        when(userDtoMapper.mapToEntity(USER_DTO_WITH_NONEXISTENT_ID))
                .thenThrow(EntityMappingException.class);
        doNothing().when(userDao).save(USER);
    }

    @Test
    public void testFindUserByEmailShouldReturnOptionalOfUserDtoWhenUserWithSuppliedEmailExists(){
        //given

        //when
        Optional<UserDto> actualOptional = service.findUserByEmail(EXISTENT_EMAIL);

        //then
        Assert.assertTrue(actualOptional.isPresent());
        UserDto actual = actualOptional.get();
        Assert.assertEquals(USER_DTO_WITH_EXISTENT_ID, actual);

        verify(userDao, times(1)).findUserByEmail(EXISTENT_EMAIL);
        verify(userDtoMapper, times(1)).mapToDto(USER);
        verifyNoMoreInteractions(userDao, userDtoMapper);
    }

    @Test
    public void testFindUserByEmailShouldReturnEmptyOptionalWhenUserWithSuppliedEmailDoesNotExist(){
        //given

        //when
        Optional<UserDto> actualOptional = service.findUserByEmail(NONEXISTENT_EMAIL);

        //then
        Assert.assertFalse(actualOptional.isPresent());

        verify(userDao, times(1)).findUserByEmail(NONEXISTENT_EMAIL);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testGetAllClients(){
        //given

        //when
        List<UserDto> actual = service.getAllClients();

        //then
        assertThat(actual, is(equalTo(EXPECTED_CLIENTS_DTO)));

        verify(userDao, times(1)).getAllClients();
        verify(userDtoMapper, times(1)).mapToDto(USER);
        verifyNoMoreInteractions(userDao, userDtoMapper);
    }

    @Test
    public void testGetClientsOfTrainer() throws ServiceException, EntityMappingException{
        //given

        //when
        List<UserDto> actual = service.getClientsOfTrainer(TRAINER_DTO);

        //then
        assertThat(actual, is(equalTo(EXPECTED_CLIENTS_DTO)));

        verify(userDtoMapper, times(1)).mapToEntity(TRAINER_DTO);
        verify(userDtoMapper, times(1)).mapToDto(USER);
        verify(userDao, times(1)).findClientsOfTrainer(TRAINER);
        verifyNoMoreInteractions(userDao, userDtoMapper);
    }

    @Test
    public void testUpdateShouldUpdateUserWhenExistentUserIdSupplied() throws ServiceException, EntityMappingException {
        //given

        //when
        service.update(USER_DTO_WITH_EXISTENT_ID);

        //then
        verify(userDao, times(1)).save(USER);
        verify(userDtoMapper, times(1)).mapToEntity(USER_DTO_WITH_EXISTENT_ID);
        verifyNoMoreInteractions(userDao, userDtoMapper);
    }

    @Test(expected = ServiceException.class)
    public void testUpdateShouldThrowExceptionWhenMappingCannotBeDone()
            throws ServiceException, EntityMappingException{
        //given

        //when
        service.update(USER_DTO_WITH_NONEXISTENT_ID);

        //then
        verify(userDtoMapper, times(1)).mapToEntity(USER_DTO_WITH_NONEXISTENT_ID);
        verifyNoMoreInteractions(userDtoMapper);
    }

}