package com.epam.fitness.service.impl;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public List<User> getClientsOfTrainer(User trainer) {
        return userDao.findClientsOfTrainer(trainer);
    }

    @Override
    public List<User> getAllClients() {
        return userDao.getAllClients();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public void setUserDiscount(int id, int discount) throws ServiceException {
        Optional<User> userOptional = userDao.findById(id);
        User user = userOptional
                .orElseThrow(() -> new ServiceException("User with the id " + id + " isn't found!"));
        user.setDiscount(discount);
        userDao.save(user);
    }

}
