package com.epam.fitness.command.impl.order;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowOrdersCommand implements Command {

    private static final String ORDERS_PAGE_URL = "/orders";
    private static final String ORDERS_ATTRIBUTE = "orders";
    private static final String USER_ATTRIBUTE = "user";

    private OrderService service;

    public ShowOrdersCommand(OrderService service){
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(USER_ATTRIBUTE);
        int id = user.getId();
        List<Order> orders = service.getOrdersByClientId(id);
        request.setAttribute(ORDERS_ATTRIBUTE, orders);
        return CommandResult.forward(ORDERS_PAGE_URL);
    }

}