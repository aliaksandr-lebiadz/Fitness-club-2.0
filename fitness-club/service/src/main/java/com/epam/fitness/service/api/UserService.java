package com.epam.fitness.service.api;

import com.epam.fitness.entity.CredentialsDto;
import com.epam.fitness.entity.SortOrder;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {

    @Transactional
    void updateById(int id, UserDto userDto) throws ServiceException;
    @Transactional
    List<UserDto> getClientsByTrainerId(int trainerId) throws ServiceException;
    List<UserDto> getAllClients();
    List<UserDto> searchUsersByParameters(String firstName, String secondName, String email, SortOrder order)
            throws ServiceException;
    UserDto getUserByEmail(String email) throws ServiceException;
    UserDto getUserWithCredentials(CredentialsDto credentialsDto) throws ServiceException;

    void create(UserDto userDto);
    UserDto getById(int id) throws ServiceException;
    void deleteById(int id) throws ServiceException;
}