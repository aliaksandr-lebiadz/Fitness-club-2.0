package com.epam.fitness.controller;

import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.utils.CurrentPageGetter;
import com.epam.fitness.validator.api.OrderValidator;
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

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final String ORDERS_PAGE = "orders";
    private static final String ORDERS_PAGE_URL = "/order/list";

    private OrderService service;
    private OrderValidator validator;
    private ControllerUtils utils;

    @Autowired
    public OrderController(OrderService service, OrderValidator validator, ControllerUtils utils){
        this.service = service;
        this.validator = validator;
        this.utils = utils;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String getOrdersPage(Model model) throws ServiceException{
        User user = utils.getCurrentUser();
        int id = user.getId();
        List<Order> orders = service.getOrdersByClientId(id);
        model.addAttribute(orders);
        return ORDERS_PAGE;
    }

    @PostMapping("/feedback")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String sendFeedback(@RequestParam("order_id") int orderId,
                               @RequestParam String feedback)
            throws ServiceException, ValidationException{
        if(!validator.isFeedbackValid(feedback)){
            throw new ValidationException("Feedback validation failed!");
        }
        service.updateFeedbackById(orderId, feedback);
        return ControllerUtils.createRedirect(ORDERS_PAGE_URL);
    }

    @PostMapping("/setNutrition")
    @PreAuthorize("hasAuthority('TRAINER')")
    public String setNutrition(@RequestParam("nutrition_type") String nutritionTypeValue,
                               @RequestParam("order_id") int orderId,
                               HttpServletRequest request)
            throws ServiceException {
        NutritionType nutritionType = NutritionType.getNutritionType(nutritionTypeValue);
        service.updateNutritionById(orderId, nutritionType);
        return ControllerUtils.createRedirect(CurrentPageGetter.getCurrentPage(request));
    }

}
