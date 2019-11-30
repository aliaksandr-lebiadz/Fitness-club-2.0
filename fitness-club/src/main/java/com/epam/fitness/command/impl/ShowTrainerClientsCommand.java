package com.epam.fitness.command.impl;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.ExerciseService;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.service.api.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowTrainerClientsCommand implements Command {

    private static final String TRAINER_CLIENTS_URL = "/trainerClients";
    private static final String USER_ATTRIBUTE = "user";
    private static final String CLIENTS_ATTRIBUTE = "clients";
    private static final String EXERCISES_ATTRIBUTE = "exercises";
    private static final String CLIENT_ORDERS_ATTRIBUTE = "client_orders";
    private static final String CLIENT_ID_PARAMETER = "client_id";

    private UserService userService;
    private OrderService orderService;
    private ExerciseService exerciseService;

    public ShowTrainerClientsCommand(UserService userService, OrderService orderService,
                                     ExerciseService exerciseService){
        this.userService = userService;
        this.orderService = orderService;
        this.exerciseService = exerciseService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException {
        HttpSession session = request.getSession();
        User trainer = (User)session.getAttribute(USER_ATTRIBUTE);
        int trainerId = trainer.getId();
        String clientIdStr = request.getParameter(CLIENT_ID_PARAMETER);
        if(clientIdStr != null){ //when a certain client is chosen
            int clientId = Integer.parseInt(clientIdStr);
            List<Order> clientOrders = orderService.getClientOrdersWithTrainerId(clientId, trainerId);
            request.setAttribute(CLIENT_ORDERS_ATTRIBUTE, clientOrders);
            List<Exercise> exercises = exerciseService.getAll();
            request.setAttribute(EXERCISES_ATTRIBUTE, exercises);
        }
        List<User> clients = userService.findUsersByTrainerId(trainerId);
        request.setAttribute(CLIENTS_ATTRIBUTE, clients);
        return CommandResult.forward(TRAINER_CLIENTS_URL);
    }
}