package com.epam.fitness.controller;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.validator.api.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final String ORDERS_PAGE_URL = "/order/list";
    private static final String USER_ATTRIBUTE = "user";
    private static final String MEMBERSHIP_SELECT_PARAMETER = "membership_select";
    private static final String CARD_NUMBER_PARAMETER = "card_number";
    private static final String VALID_THRU_PARAMETER = "valid_thru";

    private OrderService service;
    private PaymentValidator validator;

    @Autowired
    public PaymentController(OrderService service, PaymentValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @PostMapping("/getMembership")
    public String getMembership(@RequestParam(CARD_NUMBER_PARAMETER) String cardNumber,
                                @RequestParam(VALID_THRU_PARAMETER) String validThru,
                                @RequestParam String cvv,
                                @RequestParam(MEMBERSHIP_SELECT_PARAMETER) int membershipId,
                                HttpSession session)
            throws ServiceException, ValidationException {

        validatePaymentParameters(cardNumber, validThru, cvv);
        User user = (User)session.getAttribute(USER_ATTRIBUTE);
        service.create(user, membershipId);
        return ControllerUtils.createRedirect(ORDERS_PAGE_URL);
    }

    private void validatePaymentParameters(String cardNumber, String validThru, String cvv)
            throws ValidationException {
        if(!validator.isCardNumberValid(cardNumber)
                || !validator.isExpirationDateValid(validThru)
                || !validator.isCvvValid(cvv)){
            throw new ValidationException("Payment validation failed!");
        }
    }

}
