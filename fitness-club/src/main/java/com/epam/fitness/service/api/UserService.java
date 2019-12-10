package com.epam.fitness.service.api;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password) throws ServiceException;
    List<User> findUsersByTrainerId(int trainerId) throws ServiceException;
    List<User> getAllClients() throws ServiceException;
    void setUserDiscount(int id, int discount) throws ServiceException;
}