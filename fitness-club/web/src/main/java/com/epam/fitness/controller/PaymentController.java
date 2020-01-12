package com.epam.fitness.controller;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.UserNotFoundException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.validator.api.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final String ORDERS_PAGE_URL = "/order/list";

    private OrderService service;
    private PaymentValidator validator;
    private ControllerUtils utils;

    @Autowired
    public PaymentController(OrderService service, PaymentValidator validator, ControllerUtils utils){
        this.service = service;
        this.validator = validator;
        this.utils = utils;
    }

    @PostMapping("/getMembership")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String getMembership(@RequestParam("card_number") String cardNumber,
                                @RequestParam("valid_thru") String validThru,
                                @RequestParam String cvv,
                                @RequestParam("membership_select") int membershipId)
            throws ServiceException, ValidationException, UserNotFoundException {

        validatePaymentParameters(cardNumber, validThru, cvv);
        Optional<User> userOptional = utils.getCurrentUser();
        User user = userOptional.orElseThrow(UserNotFoundException::new);
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
