package com.epam.fitness.validator.impl;

import com.epam.fitness.utils.DateUtils;
import com.epam.fitness.validator.api.AssignmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AssignmentValidatorImpl implements AssignmentValidator {

    private DateUtils dateUtils;

    @Autowired
    public AssignmentValidatorImpl(DateUtils dateUtils){
        this.dateUtils = dateUtils;
    }

    @Override
    public boolean isWorkoutDateValid(Date workoutDate){
        return isNotBeforeToday(workoutDate);
    }

    private boolean isNotBeforeToday(Date date){
        Date today = dateUtils.getCurrentDateWithoutTime();
        return !date.before(today);
    }

}
