package com.epam.fitness.controller;

import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dao.impl.GymMembershipDaoImpl;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.assignment.Exercise;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    public UserDao userDao() {
        return mock(UserDao.class);
    }

    @Bean
    @Primary
    public Dao<GymMembership> gymMembershipDao() {
        return mock(GymMembershipDaoImpl.class);
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return mock(PasswordEncoder.class);
    }

    @Bean
    @Primary
    public OrderDao orderDao() {
        return mock(OrderDao.class);
    }

    @Bean
    @Primary
    public AssignmentDao assignmentDao() {
        return mock(AssignmentDao.class);
    }

    @Bean
    @Primary
    public Dao<Exercise> exerciseDao() {
        return (Dao<Exercise>) mock(Dao.class);
    }

}
