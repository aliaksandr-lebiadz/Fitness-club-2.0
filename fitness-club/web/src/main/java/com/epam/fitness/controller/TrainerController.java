package com.epam.fitness.controller;

import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.ExerciseService;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    private static final String TRAINER_CLIENTS_PAGE = "trainer_clients";
    private static final String CLIENTS_ATTRIBUTE = "clients";
    private static final String EXERCISES_ATTRIBUTE = "exercises";
    private static final String CLIENT_ORDERS_ATTRIBUTE = "client_orders";

    private UserService userService;
    private OrderService orderService;
    private ExerciseService exerciseService;
    private ControllerUtils utils;

    @Autowired
    public TrainerController(UserService userService, OrderService orderService,
                                     ExerciseService exerciseService, ControllerUtils utils){
        this.userService = userService;
        this.orderService = orderService;
        this.exerciseService = exerciseService;
        this.utils = utils;
    }

    @GetMapping("/clients")
    @PreAuthorize("hasAuthority('TRAINER')")
    public String getTrainerClientsPage(
            @RequestParam("client_id") Optional<Integer> optionalClientId, Model model)
            throws ServiceException {
        User trainer = utils.getCurrentUser();
        int trainerId = trainer.getId();
        if(optionalClientId.isPresent()){
            int clientId = optionalClientId.get();
            setOrdersAndExercises(clientId, trainerId, model);
        }
        List<User> clients = userService.findUsersByTrainerId(trainerId);
        model.addAttribute(CLIENTS_ATTRIBUTE, clients);
        return TRAINER_CLIENTS_PAGE;
    }

    private void setOrdersAndExercises(int clientId, int trainerId, Model model){
        List<Order> clientOrders = orderService.getClientOrdersWithTrainerId(clientId, trainerId);
        model.addAttribute(CLIENT_ORDERS_ATTRIBUTE, clientOrders);
        List<Exercise> exercises = exerciseService.getAll();
        model.addAttribute(EXERCISES_ATTRIBUTE, exercises);
    }

}
