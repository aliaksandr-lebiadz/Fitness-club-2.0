package com.epam.fitness.controller;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.UserService;
import com.epam.fitness.validator.api.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private UserService service;
    private UserValidator validator;

    @Autowired
    public ClientController(UserService service, UserValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getClientsPage(Model model){
        List<User> clients = service.getAllClients();
        model.addAttribute(clients);
        return CLIENTS_PAGE;
    }

    @PostMapping("/setDiscount")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String setClientDiscount(@RequestParam("user_id") int userId,
                                    @RequestParam int discount)
            throws ServiceException, ValidationException{
        if(!validator.isDiscountValid(discount)){
            throw new ValidationException("Discount validation failed!");
        }
        service.setUserDiscount(userId, discount);
        return ControllerUtils.createRedirect(CLIENTS_PAGE_URL);
    }

}
