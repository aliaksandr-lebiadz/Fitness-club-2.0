package com.epam.fitness.validator;

import com.epam.fitness.validator.api.UserValidator;
import com.epam.fitness.validator.impl.UserValidatorImpl;
import org.junit.Assert;
import org.junit.Test;

public class UserValidatorImplTest {

    private static final int VALID_DISCOUNT = 52;
    private static final int NEGATIVE_DISCOUNT = -5;
    private static final int EXCESS_DISCOUNT = 150;

    private UserValidator validator = new UserValidatorImpl();

    @Test
    public void testIsDiscountValidShouldReturnTrueWhenValidDiscountSupplied(){
        //given

        //when
        boolean actual = validator.isDiscountValid(VALID_DISCOUNT);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void testIsDiscountValidShouldReturnFalseWhenNegativeDiscountSupplied(){
        //given

        //when
        boolean actual = validator.isDiscountValid(NEGATIVE_DISCOUNT);

        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsDiscountValidShouldReturnFalseWhenExcessDiscountSupplied(){
        //given

        //when
        boolean actual = validator.isDiscountValid(EXCESS_DISCOUNT);

        //then
        Assert.assertFalse(actual);
    }

}