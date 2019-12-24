package com.epam.fitness.command.impl.order;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;

import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.utils.CurrentPageGetter;
import com.epam.fitness.validator.api.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.fitness.command.Commands.SEND_FEEDBACK_COMMAND;

@Component(SEND_FEEDBACK_COMMAND)
public class SendFeedbackCommand implements Command {

    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final String FEEDBACK_PARAMETER = "feedback";

    private OrderService service;
    private OrderValidator validator;

    @Autowired
    public SendFeedbackCommand(OrderService service, OrderValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, ValidationException {
        String feedback = request.getParameter(FEEDBACK_PARAMETER);
        if(!validator.isFeedbackValid(feedback)){
            throw new ValidationException("Feedback validation failed!");
        }
        String orderIdStr = request.getParameter(ORDER_ID_PARAMETER);
        int orderId = Integer.parseInt(orderIdStr);
        service.updateFeedbackById(orderId, feedback);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.redirect(currentPage);
    }
}