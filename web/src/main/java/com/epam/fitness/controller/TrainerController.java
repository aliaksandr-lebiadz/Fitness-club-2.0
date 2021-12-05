package com.epam.fitness.controller;

import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.SignUpRequestDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.UserNotFoundException;
import com.epam.fitness.service.api.ExerciseService;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.service.api.UserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Optional;

@Controller
@Validated
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

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addTrainer(@RequestParam @Length(min = 7, max = 30) @Email String email,
                             @RequestParam @Length(min = 5, max = 20) String password,
                             @RequestParam("first_name") @Length(min = 3, max = 30) String firstName,
                             @RequestParam("second_name") @Length(min = 3, max = 30) String secondName) throws ServiceException{
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(email, password, firstName, secondName, UserRole.TRAINER);
        userService.signUp(signUpRequestDto);
        return ControllerUtils.createRedirect("/user/list");
    }

    @GetMapping("/clients")
    @PreAuthorize("hasAuthority('TRAINER')")
    public String getTrainerClientsPage(
            @RequestParam("client_id") Optional<Integer> optionalClientId, Model model)
            throws UserNotFoundException, ServiceException {
        Optional<UserDto> trainerOptional = utils.getCurrentUser();
        UserDto trainer = trainerOptional.orElseThrow(UserNotFoundException::new);

        if(optionalClientId.isPresent()){
            int clientId = optionalClientId.get();
            setOrdersAndExercises(clientId, trainer, model);
        }
        List<UserDto> clients = userService.getClientsOfTrainer(trainer);
        model.addAttribute(CLIENTS_ATTRIBUTE, clients);
        return TRAINER_CLIENTS_PAGE;
    }

    private void setOrdersAndExercises(int clientId, UserDto trainer, Model model)
            throws ServiceException{
        List<OrderDto> clientOrders = orderService.getOrdersOfTrainerClient(clientId, trainer);
        model.addAttribute(CLIENT_ORDERS_ATTRIBUTE, clientOrders);
        List<ExerciseDto> exercises = exerciseService.getAll();
        model.addAttribute(EXERCISES_ATTRIBUTE, exercises);
    }

}
