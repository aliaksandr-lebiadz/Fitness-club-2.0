package com.epam.fitness.controller.rest;

import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService){
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public List<UserDto> getUsers(){
        return userService.getAll();
    }

    @GetMapping("/{id}/clients")
    public List<UserDto> getClientsByTrainerId(@PathVariable int id) throws ServiceException{
        return userService.getClientsByTrainerId(id);
    }

    @GetMapping("/clients")
    public List<UserDto> getClients() throws ServiceException{
        return userService.getAllClients();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) throws ServiceException{
        return userService.getById(id);
    }

    @GetMapping("/search")
    public List<UserDto> searchUsersByParameters(@RequestParam(value = "first_name", required = false) String firstName,
                                        @RequestParam(value = "second_name", required = false) String secondName,
                                        @RequestParam(required = false) String email) throws ServiceException{
        return userService.searchUsersByParameters(firstName, secondName, email);
    }

    @PostMapping
    public void createUser(@Valid @RequestBody UserDto user) throws ServiceException {
        userService.create(user);
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable int id, @Valid @RequestBody UserDto user) throws ServiceException{
        userService.updateById(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) throws ServiceException{
        userService.deleteById(id);
    }

    @GetMapping("/{id}/orders")
    public List<OrderDto> getOrdersByClientId(@PathVariable int id) throws ServiceException{
        return orderService.getOrdersByClientId(id);
    }

    @PostMapping("/{id}/orders")
    public void createOrder(@PathVariable int id,
                            @RequestBody GymMembershipDto gymMembershipDto)
            throws ServiceException{
        orderService.create(id, gymMembershipDto);
    }

    @GetMapping("/sort")
    public List<UserDto> sortUsers(@RequestParam boolean asc){
        return userService.sortUsers(asc);
    }

}
