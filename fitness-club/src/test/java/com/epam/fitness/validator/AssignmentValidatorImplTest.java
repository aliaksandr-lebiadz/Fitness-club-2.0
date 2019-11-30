package com.epam.fitness.validator;

import com.epam.fitness.validator.api.AssignmentValidator;
import com.epam.fitness.validator.impl.AssignmentValidatorImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class AssignmentValidatorImplTest {

    private static final int VALID_AMOUNT = 5;
    private static final int NEGATIVE_AMOUNT = -2;
    private static final int ZERO_AMOUNT = 0;
    private static final int LARGE_AMOUNT = 110;

    private AssignmentValidator validator = new AssignmentValidatorImpl();

    /* testing isAmountOfSetsValid method */

    @Test
    public void testIsAmountOfSetsValidShouldReturnFalseWhenLargeAmountOfSetsSupplied(){
        //given

        //when
        boolean actual = validator.isAmountOfSetsValid(LARGE_AMOUNT);

        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsAmountOfSetsValidShouldReturnFalseWhenZeroAmountSupplied(){
        //given

        //when
        boolean actual = validator.isAmountOfSetsValid(ZERO_AMOUNT);

        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsAmountOfSetsValidShouldReturnTrueWhenValidAmountOfSetsSupplied(){
        //given

        //when
        boolean actual = validator.isAmountOfSetsValid(VALID_AMOUNT);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void testIsAmountOfSetsValidShouldReturnFalseWhenNegativeAmountSupplied(){
        //given

        //when
        boolean actual = validator.isAmountOfSetsValid(NEGATIVE_AMOUNT);

        //then
        Assert.assertFalse(actual);
    }

    /* testing isAmountOfRepsValid method */

    @Test
    public void testIsAmountOfRepsValidShouldReturnFalseWhenNegativeAmountSupplied(){
        //given

        //when
        boolean actual = validator.isAmountOfRepsValid(NEGATIVE_AMOUNT);

        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsAmountOfRepsValidShouldReturnTrueWhenValidAmountOfRepsSupplied(){
        //given

        //when
        boolean actual = validator.isAmountOfRepsValid(VALID_AMOUNT);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void testIsAmountOfRepsValidShouldReturnFalseWhenZeroAmountOfRepsSupplied(){
        //given

        //when
        boolean actual = validator.isAmountOfRepsValid(ZERO_AMOUNT);

        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsAmountOfRepsValidShouldReturnFalseWhenLargeAmountOfRepsSupplied(){
        //given

        //when
        boolean actual = validator.isAmountOfRepsValid(LARGE_AMOUNT);

        //then
        Assert.assertFalse(actual);
    }


    /* testing isWorkoutDateValid method */

    @Test
    public void testIsWorkoutDateValidShouldReturnTrueWhenValidDateSupplied(){
        //given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date validDate = calendar.getTime();

        //when
        boolean actual = validator.isWorkoutDateValid(validDate);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void testIsWorkoutDateValidShouldReturnFalseWhenInvalidDateSupplied(){
        //given
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date invalidDate = calendar.getTime();

        //when
        boolean actual = validator.isWorkoutDateValid(invalidDate);

        //then
        Assert.assertFalse(actual);
    }

}