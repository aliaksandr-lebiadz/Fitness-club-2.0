package com.epam.fitness.service.api;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void setUserDiscount(int id, int discount) throws ServiceException;
    List<User> findUsersByTrainerId(int trainerId);
    List<User> getAllClients();
    Optional<User> findUserByEmail(String email);
}