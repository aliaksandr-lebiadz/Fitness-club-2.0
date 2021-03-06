package com.epam.fitness.controller;

import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.SortOrder;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(value = "first_name", required = false) String firstName,
                                  @RequestParam(value = "second_name", required = false) String secondName,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) SortOrder order)
            throws ServiceException{
        return userService.searchUsersByParameters(firstName, secondName, email, order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto user) throws ServiceException{
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable int id, @Valid @RequestBody UserDto user) throws ServiceException{
        return userService.updateById(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable int id) throws ServiceException{
        userService.deleteById(id);
    }

    @GetMapping("/{id}/orders")
    public List<OrderDto> getOrdersByClientId(@PathVariable int id,
                                              @RequestParam(value = "trainer_id", required = false) Integer trainerId)
            throws ServiceException{
        if(Objects.nonNull(trainerId)){
            return orderService.getOrdersOfTrainerClient(id, trainerId);
        } else{
            return orderService.getOrdersByClientId(id);
        }
    }

    @PostMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@PathVariable int id,
                                @RequestParam("gym_membership_id") int gymMembershipId)
            throws ServiceException{
       return orderService.create(id, gymMembershipId);
    }

}
