package com.epam.fitness.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public Date getFirstDayOfCurrentMonth(){
        Date now = new Date();
        return org.apache.commons.lang.time.DateUtils.truncate(now, Calendar.MONTH);
    }

    public Date getCurrentDateWithoutTime(){
        Date now = new Date();
        return org.apache.commons.lang.time.DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
    }
}
