package com.epam.fitness.controller;

import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.GymMembershipService;
import com.epam.fitness.service.api.UserService;
import com.epam.fitness.validator.api.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private static final String CLIENTS_PAGE = "clients";
    private static final String CLIENTS_PAGE_URL = "/client/list";

    private UserService userService;
    private UserValidator userValidator;

    @Autowired
    public ClientController(UserService userService,
                            UserValidator userValidator){
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/list")
    public String getClientsPage(Model model){
        List<User> clients = userService.getAllClients();
        model.addAttribute(clients);
        return CLIENTS_PAGE;
    }

    @PostMapping("/setDiscount")
    public String setClientDiscount(@RequestParam("user_id") int userId,
                                    @RequestParam int discount)
            throws ServiceException, ValidationException{
        if(!userValidator.isDiscountValid(discount)){
            throw new ValidationException("Discount validation failed!");
        }
        userService.setUserDiscount(userId, discount);
        return ControllerUtils.createRedirect(CLIENTS_PAGE_URL);
    }

}
