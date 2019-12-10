package com.epam.fitness.validator.impl;

import com.epam.fitness.validator.api.UserValidator;

public class UserValidatorImpl implements UserValidator {

    private static final int MIN_DISCOUNT = 0;
    private static final int MAX_DISCOUNT = 100;

    @Override
    public boolean isDiscountValid(int discount){
        return discount >= MIN_DISCOUNT && discount <= MAX_DISCOUNT;
    }

}