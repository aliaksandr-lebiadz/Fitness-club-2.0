package com.epam.fitness.utils;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtils {

    public Date getFirstDayOfCurrentMonth(){
        return truncateCurrentDateByField(Calendar.MONTH);
    }

    public Date getCurrentDateWithoutTime(){
        return truncateCurrentDateByField(Calendar.DAY_OF_MONTH);
    }

    private Date truncateCurrentDateByField(int field){
        Date currentDate = new Date();
        return org.apache.commons.lang.time.DateUtils.truncate(currentDate, field);
    }
}
