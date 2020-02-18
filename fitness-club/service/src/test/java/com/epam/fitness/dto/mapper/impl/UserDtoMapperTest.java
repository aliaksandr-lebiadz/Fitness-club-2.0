package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.DtoMappingException;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class UserDtoMapperTest {

    private static final User FIRST_USER = new User(1, "admin@gmail.com", "admin", UserRole.ADMIN,
            "Alex", "Lopez", 33);
    private static final User SECOND_USER = new User(7, "client@gmail.com", "client", UserRole.CLIENT,
            "Mikhail", "Lozopv", 5);

    private static final UserDto FIRST_USER_DTO = new UserDto(1, "admin@gmail.com", "admin", UserRole.ADMIN,
            "Alex", "Lopez", 33);
    private static final UserDto SECOND_USER_DTO = new UserDto(7, "client@gmail.com", "client", UserRole.CLIENT,
            "Mikhail", "Lozopv", 5);

    private static final List<User> USERS = Arrays.asList(
            FIRST_USER,
            SECOND_USER
    );
    private static final List<UserDto> USERS_DTO = Arrays.asList(
            FIRST_USER_DTO,
            SECOND_USER_DTO
    );
    private static final List<User> NULL_USERS = Arrays.asList(
            null,
            null,
            null
    );

    private ModelMapper modelMapper = new ModelMapper();
    private DtoMapper<User, UserDto> mapper = new UserDtoMapper(modelMapper);

    @Test
    public void mapToDto() throws DtoMappingException {
        //given

        //when
        UserDto actual = mapper.mapToDto(FIRST_USER);

        //then
        assertEquals(FIRST_USER_DTO, actual);
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoWithDtoMappingException() throws DtoMappingException {
        //given
        final User user = null;

        //when
        mapper.mapToDto(user);

        //then
    }

    @Test
    public void mapToEntity() throws DtoMappingException {
        //given

        //when
        User actual = mapper.mapToEntity(SECOND_USER_DTO);

        //then
        assertEquals(SECOND_USER.getId(), actual.getId());
        assertEquals(SECOND_USER.getEmail(), actual.getEmail());
        assertEquals(SECOND_USER.getPassword(), actual.getPassword());
        assertEquals(SECOND_USER.getFirstName(), actual.getFirstName());
        assertEquals(SECOND_USER.getSecondName(), actual.getSecondName());
        assertEquals(SECOND_USER.getRole(), actual.getRole());
        assertEquals(SECOND_USER.getDiscount(), actual.getDiscount());
    }

    @Test(expected = DtoMappingException.class)
    public void mapToEntityWithDtoMappingException() throws DtoMappingException {
        //given
        final UserDto userDto = null;

        //when
        mapper.mapToEntity(userDto);

        //then
    }

    @Test
    public void mapToDtoList() throws DtoMappingException {
        //given

        //when
        List<UserDto> actual = mapper.mapToDto(USERS);

        //then
        assertThat(actual, is(equalTo(USERS_DTO)));
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoListWithDtoMappingException() throws DtoMappingException {
        //given

        //when
        mapper.mapToDto(NULL_USERS);

        //then
    }

}