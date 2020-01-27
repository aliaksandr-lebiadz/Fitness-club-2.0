package com.epam.fitness.controller;

import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.UserNotFoundException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.utils.CurrentPageGetter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final String ORDERS_PAGE = "orders";
    private static final String ORDERS_PAGE_URL = "/order/list";
    private static final String ORDERS_ATTRIBUTE = "orderList";

    private OrderService service;
    private ControllerUtils utils;

    @Autowired
    public OrderController(OrderService service, ControllerUtils utils){
        this.service = service;
        this.utils = utils;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String getOrdersPage(Model model) throws UserNotFoundException, ServiceException{
        Optional<UserDto> userOptional = utils.getCurrentUser();
        UserDto user = userOptional.orElseThrow(UserNotFoundException::new);

        List<OrderDto> orders = service.getOrdersOfClient(user);
        model.addAttribute(ORDERS_ATTRIBUTE, orders);
        return ORDERS_PAGE;
    }

    @PostMapping("/feedback")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String sendFeedback(@RequestParam("order_id") int orderId,
                               @RequestParam @Length(min = 10, max = 1000) String feedback)
            throws ServiceException{
        OrderDto orderDto = new OrderDto(orderId, feedback);
        service.update(orderDto);
        return ControllerUtils.createRedirect(ORDERS_PAGE_URL);
    }

    @PostMapping("/setNutrition")
    @PreAuthorize("hasAuthority('TRAINER')")
    public String setNutrition(@RequestParam("order_id") int orderId,
                               @RequestParam("nutrition_type") String nutritionTypeValue,
                               HttpServletRequest request)
            throws ServiceException {
        NutritionType nutritionType = NutritionType.valueOf(nutritionTypeValue);
        OrderDto orderDto = new OrderDto(orderId, nutritionType);
        service.update(orderDto);
        return ControllerUtils.createRedirect(CurrentPageGetter.getCurrentPage(request));
    }

}
