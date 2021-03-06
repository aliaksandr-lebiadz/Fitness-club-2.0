package com.epam.fitness.controller;

import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private AssignmentService assignmentService;

    @Autowired
    public OrderController(OrderService orderService, AssignmentService assignmentService){
        this.orderService = orderService;
        this.assignmentService = assignmentService;
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable int id) throws ServiceException{
        return orderService.getById(id);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrderById(@PathVariable int id, @Valid @RequestBody OrderDto orderDto) throws ServiceException{
        return orderService.updateById(id, orderDto);
    }

    @GetMapping("/{id}/assignments")
    public List<AssignmentDto> getAssignmentsByOrderId(@PathVariable int id) throws ServiceException{
        return assignmentService.getAllByOrderId(id);
    }

    @PostMapping("/{id}/assignments")
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentDto createAssignment(@PathVariable int id, @Valid @RequestBody AssignmentDto assignmentDto)
            throws ServiceException{
        return assignmentService.create(id, assignmentDto);
    }

}
