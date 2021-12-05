package com.epam.fitness.controller;

import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.UserNotFoundException;
import com.epam.fitness.service.api.OrderService;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Optional;

@Controller
@Validated
@RequestMapping("/payment")
public class PaymentController {

    private static final String ORDERS_PAGE_URL = "/order/list";
    private static final String EXPIRATION_DATE_FORMAT = "MM/yy";

    private OrderService service;
    private ControllerUtils utils;

    @Autowired
    public PaymentController(OrderService service, ControllerUtils utils){
        this.service = service;
        this.utils = utils;
    }

    @PostMapping("/getMembership")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String getMembership(@RequestParam("card_number") @Pattern(regexp = "\\d{16}") @CreditCardNumber String cardNumber,
                                @RequestParam("valid_thru")
                                    @DateTimeFormat(pattern = EXPIRATION_DATE_FORMAT) @FutureOrPresent Date expirationDate,
                                @RequestParam @Pattern(regexp = "\\d{3}") String cvv,
                                @RequestParam("membership_select") int membershipId)
            throws ServiceException, UserNotFoundException {
        Optional<UserDto> userOptional = utils.getCurrentUser();
        UserDto user = userOptional.orElseThrow(UserNotFoundException::new);
        service.create(user, membershipId);
        return ControllerUtils.createRedirect(ORDERS_PAGE_URL);
    }

}
