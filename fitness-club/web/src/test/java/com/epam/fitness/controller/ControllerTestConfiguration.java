package com.epam.fitness.controller;

import com.epam.fitness.service.api.*;
import com.epam.fitness.validator.api.AssignmentValidator;
import com.epam.fitness.validator.api.OrderValidator;
import com.epam.fitness.validator.api.PaymentValidator;
import com.epam.fitness.validator.api.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Profile("test")
@Configuration
public class ControllerTestConfiguration {

    @Bean
    @Primary
    public GymMembershipService gymMembershipService(){
        return mock(GymMembershipService.class);
    }

    @Bean
    @Primary
    public UserService userService(){
        return mock(UserService.class);
    }

    @Bean
    @Primary
    public AssignmentService assignmentService(){
        return mock(AssignmentService.class);
    }

    @Bean
    @Primary
    public ExerciseService exerciseService(){
        return mock(ExerciseService.class);
    }

    @Bean
    @Primary
    public OrderService orderService() {
        return mock(OrderService.class);
    }

    @Bean
    @Primary
    public UserValidator userValidator(){
        return mock(UserValidator.class);
    }

    @Bean
    @Primary
    public AssignmentValidator assignmentValidator() {
        return mock(AssignmentValidator.class);
    }

    @Bean
    @Primary
    public OrderValidator orderValidator(){
        return mock(OrderValidator.class);
    }

    @Bean
    @Primary
    public PaymentValidator paymentValidator(){
        return mock(PaymentValidator.class);
    }

    @Bean
    @Primary
    public ControllerUtils controllerUtils(){
        return mock(ControllerUtils.class);
    }

}
