package com.epam.fitness.validator.impl;

import com.epam.fitness.utils.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;

import static org.mockito.Mockito.*;

public class AssignmentValidatorImplTest {

    private static final String VALID_WORKOUT_DATE = "2019-10-01";
    private static final String INVALID_WORKOUT_DATE = "2019-09-30";
    private static final String CURRENT_DATE_WITHOUT_TIME = "2019-10-01";

    @Mock
    private DateUtils dateUtils;

    @InjectMocks
    private AssignmentValidatorImpl validator;

    @Before
    public void createMocks() {
        MockitoAnnotations.initMocks(this);

        Date currentDateWithoutTime = Date.valueOf(CURRENT_DATE_WITHOUT_TIME);
        when(dateUtils.getCurrentDateWithoutTime()).thenReturn(currentDateWithoutTime);
    }

    @Test
    public void testIsWorkoutDateValidShouldReturnTrueWhenValidDateSupplied(){
        //given
        Date validWorkoutDate = Date.valueOf(VALID_WORKOUT_DATE);

        //when
        boolean actual = validator.isWorkoutDateValid(validWorkoutDate);

        //then
        Assert.assertTrue(actual);
        verify(dateUtils, times(1)).getCurrentDateWithoutTime();
    }
    @Test
    public void testIsWorkoutDateValidShouldReturnFalseWhenInvalidDateSupplied(){
        //given
        Date invalidWorkoutDate = Date.valueOf(INVALID_WORKOUT_DATE);

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