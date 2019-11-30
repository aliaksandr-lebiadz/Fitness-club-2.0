package com.epam.fitness.command.impl;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.validator.api.PaymentValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMembershipCommand implements Command {

    private static final String ORDERS_PAGE = "/controller?command=showOrders";
    private static final String USER_ATTRIBUTE = "user";
    private static final String MEMBERSHIP_SELECT_PARAMETER = "membership_select";
    private static final String CARD_NUMBER_PARAMETER = "card_number";
    private static final String VALID_THRU_PARAMETER = "valid_thru";
    private static final String CVV_PARAMETER = "cvv";

    private OrderService orderService;
    private PaymentValidator paymentValidator;

    public GetMembershipCommand(OrderService orderService, PaymentValidator paymentValidator){
        this.orderService = orderService;
        this.paymentValidator = paymentValidator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, ValidationException {
        String cardNumber = request.getParameter(CARD_NUMBER_PARAMETER);
        String validThru = request.getParameter(VALID_THRU_PARAMETER);
        String cvv = request.getParameter(CVV_PARAMETER);

        checkPaymentParameters(cardNumber, validThru, cvv);

        //there should be payment process
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(USER_ATTRIBUTE);
        String membershipIdStr = request.getParameter(MEMBERSHIP_SELECT_PARAMETER);
        int membershipId = Integer.parseInt(membershipIdStr);
        orderService.create(user, membershipId);
        return CommandResult.redirect(ORDERS_PAGE);
    }

    private void checkPaymentParameters(String cardNumber, String validThru, String cvv)
            throws ValidationException{
        if(!paymentValidator.isCardNumberValid(cardNumber)
                || !paymentValidator.isExpirationDateValid(validThru)
                || !paymentValidator.isCvvValid(cvv)){
            throw new ValidationException("Payment validation failed!");
        }
    }
}