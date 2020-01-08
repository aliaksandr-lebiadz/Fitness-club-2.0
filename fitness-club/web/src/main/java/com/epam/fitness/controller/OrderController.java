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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final String ORDERS_PAGE = "orders";
    private static final String ORDERS_PAGE_URL = "/order/list";
    private static final String USER_ATTRIBUTE = "user";

    private OrderService service;
    private OrderValidator validator;

    @Autowired
    public OrderController(OrderService service, OrderValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @GetMapping("/list")
    public String getOrdersPage(HttpSession session, Model model){
        User user = (User)session.getAttribute(USER_ATTRIBUTE);
        int id = user.getId();
        List<Order> orders = service.getOrdersByClientId(id);
        model.addAttribute(orders);
        return ORDERS_PAGE;
    }

    @PostMapping("/feedback")
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
    public String setNutrition(@RequestParam("nutrition_type") String nutritionTypeValue,
                               @RequestParam("order_id") int orderId,
                               HttpServletRequest request)
            throws ServiceException {
        NutritionType nutritionType = NutritionType.getNutritionType(nutritionTypeValue);
        service.updateNutritionById(orderId, nutritionType);
        return ControllerUtils.createRedirect(CurrentPageGetter.getCurrentPage(request));
    }

}
