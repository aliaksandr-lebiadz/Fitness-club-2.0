package com.epam.fitness.service.api;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void setUserDiscount(int id, int discount) throws ServiceException;
    Optional<User> login(String email, String password);
    List<User> findUsersByTrainerId(int trainerId);
    List<User> getAllClients();
}