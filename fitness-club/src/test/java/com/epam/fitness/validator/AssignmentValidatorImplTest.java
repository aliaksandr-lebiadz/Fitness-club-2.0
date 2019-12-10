package com.epam.fitness.validator;

import com.epam.fitness.utils.DateUtils;
import com.epam.fitness.validator.api.AssignmentValidator;
import com.epam.fitness.validator.impl.AssignmentValidatorImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;

public class AssignmentValidatorImplTest {

    private static final int VALID_AMOUNT = 5;
    private static final int NEGATIVE_AMOUNT = -2;
    private static final int ZERO_AMOUNT = 0;
    private static final int LARGE_AMOUNT = 110;
    private static final String VALID_WORKOUT_DATE = "01/10/19";
    private static final String INVALID_WORKOUT_DATE = "30/09/19";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
    private static final String CURRENT_DATE_WITHOUT_TIME = "01/10/19";

    private DateUtils dateUtils = mock(DateUtils.class);
    private AssignmentValidator validator = new AssignmentValidatorImpl(dateUtils);

    @Before
    public void createMocks() throws ParseException {
        Date currentDateWithoutTime = DATE_FORMAT.parse(CURRENT_DATE_WITHOUT_TIME);
        when(dateUtils.getCurrentDateWithoutTime()).thenReturn(currentDateWithoutTime);
    }

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
    public void testIsWorkoutDateValidShouldReturnTrueWhenValidDateSupplied() throws ParseException{
        //given
        Date validWorkoutDate = DATE_FORMAT.parse(VALID_WORKOUT_DATE);

        //when
        boolean actual = validator.isWorkoutDateValid(validWorkoutDate);

        //then
        Assert.assertTrue(actual);
        verify(dateUtils, times(1)).getCurrentDateWithoutTime();
    }

    @Test
    public void testIsWorkoutDateValidShouldReturnFalseWhenInvalidDateSupplied() throws ParseException{
        //given
        Date invalidWorkoutDate = DATE_FORMAT.parse(INVALID_WORKOUT_DATE);

        //when
        boolean actual = validator.isWorkoutDateValid(invalidWorkoutDate);

        //then
        Assert.assertFalse(actual);
        verify(dateUtils, times(1)).getCurrentDateWithoutTime();
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(dateUtils);
    }

}