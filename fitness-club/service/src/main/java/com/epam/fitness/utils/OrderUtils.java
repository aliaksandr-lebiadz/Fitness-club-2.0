package com.epam.fitness.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>An utils class for operations, related with a
 * {@link com.epam.fitness.entity.order.Order} entity.</p>
 */
@Component
public class OrderUtils {

    /**
     * <p>A maximal percentage discount.</p>
     */
    private static final double MAX_DISCOUNT = 100.0;

    /**
     * <p>Calculates a {@link Date} after the supplied number of months.</p>
     *
     * @param monthsAmount a number of months
     * @return the {@link Date} after the supplied months amount
     */
    public LocalDateTime getDateAfterMonthsAmount(int monthsAmount){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, monthsAmount);
        Date date = calendar.getTime();
        long time = date.getTime();
        return new Timestamp(time).toLocalDateTime();
    }

    /**
     * <p>Calculates a new price according to the supplied discount.</p>
     *
     * @param initialPrice an initial price
     * @param discount an percentage discount
     * @return the price with discount
     */
    public BigDecimal calculatePriceWithDiscount(BigDecimal initialPrice, int discount){
        double discountRateValue = (MAX_DISCOUNT - discount) / MAX_DISCOUNT;
        BigDecimal discountRate = BigDecimal.valueOf(discountRateValue);
        return initialPrice.multiply(discountRate);
    }

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

}