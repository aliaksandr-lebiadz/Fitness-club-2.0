package com.epam.fitness.validator.api;

public interface PaymentValidator {

    boolean isCardNumberValid(String cardNumber);
    boolean isExpirationDateValid(String expirationDate);
    boolean isCvvValid(String cvv);

}
