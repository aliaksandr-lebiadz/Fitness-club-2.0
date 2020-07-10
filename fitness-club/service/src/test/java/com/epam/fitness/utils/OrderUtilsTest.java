package com.epam.fitness.utils;

import org.hamcrest.Matchers;
import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderUtilsTest {

    private static final double DOUBLE_DELTA = 1e-7;

    private OrderUtils utils = new OrderUtils();

    @Test
    public void calculatePriceWithDiscountWhenZeroSupplied(){
        //given
        final int discount = 0;
        final BigDecimal initialPrice = BigDecimal.valueOf(2.55);
        final BigDecimal expected = BigDecimal.valueOf(2.55);

        //when
        BigDecimal actual = utils.calculatePriceWithDiscount(initialPrice, discount);

        //then
        assertThat(actual, Matchers.comparesEqualTo(expected));
    }

    @Test
    public void calculatePriceWithDiscountWhenOneHundredSupplied(){
        //given
        final int discount = 100;
        final BigDecimal initialPrice = BigDecimal.valueOf(52.1);
        final double expected = 0.0;

        //when
        BigDecimal actual = utils.calculatePriceWithDiscount(initialPrice, discount);

        //then
        assertEquals(expected, actual.doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void calculatePriceWithDiscountWhenFiftySupplied(){
        //given
        final int discount = 50;
        final BigDecimal initialPrice = BigDecimal.valueOf(11.77);
        final double expected = 5.885;

        //when
        BigDecimal actual = utils.calculatePriceWithDiscount(initialPrice, discount);

        //then
        assertEquals(expected, actual.doubleValue(), DOUBLE_DELTA);
    }

}