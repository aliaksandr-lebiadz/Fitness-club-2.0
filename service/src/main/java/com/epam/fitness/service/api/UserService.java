package com.epam.fitness.service.api;

import com.epam.fitness.entity.SignUpRequestDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void update(UserDto userDto) throws ServiceException;

    List<UserDto> getClientsOfTrainer(UserDto trainer) throws ServiceException;

    List<UserDto> getAll();

    Optional<UserDto> findUserByEmail(String email);

    UserDto signUp(SignUpRequestDto signUpRequestDto) throws ServiceException;

}