package com.epam.fitness.controller;

import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private static final String CLIENTS_PAGE = "clients";
    private static final String CLIENTS_PAGE_URL = "/client/list";
    private static final String CLIENTS_ATTRIBUTE = "userList";

    private UserService service;

    @Autowired
    public ClientController(UserService service){
        this.service = service;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getClientsPage(Model model){
        List<UserDto> clients = service.getAllClients();
        model.addAttribute(CLIENTS_ATTRIBUTE, clients);
        return CLIENTS_PAGE;
    }

    @PostMapping("/setDiscount")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String setClientDiscount(@RequestParam("user_id") int userId,
                                    @RequestParam @Min(0) @Max(100) int discount) throws ServiceException{
        UserDto userDto = new UserDto(userId, discount);
        service.update(userDto);
        return ControllerUtils.createRedirect(CLIENTS_PAGE_URL);
    }

}
