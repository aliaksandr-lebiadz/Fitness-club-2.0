package com.epam.fitness.utils;

import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;

public class OrderUtilsTest {

    private static final double DOUBLE_DELTA = 1e-7;

    private OrderUtils utils = new OrderUtils();

    @Test
    public void testCalculatePriceWithDiscountShouldReturnSamePriceWhenZeroSupplied(){
        //given
        final int discount = 0;
        final BigDecimal initialPrice = new BigDecimal(2.55);
        final BigDecimal expected = new BigDecimal(2.55);

        //when
        BigDecimal actual = utils.calculatePriceWithDiscount(initialPrice, discount);

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCalculatePriceWithDiscountShouldReturnZeroWhenOneHundredPercentDiscountSupplied(){
        //given
        final int discount = 100;
        final BigDecimal initialPrice = new BigDecimal(52.1);
        final double expected = 0.0;

        //when
        BigDecimal actual = utils.calculatePriceWithDiscount(initialPrice, discount);

        //then
        Assert.assertEquals(expected, actual.doubleValue(), DOUBLE_DELTA);
    }

    @Test
    public void testCalculatePriceWithDiscountShouldReturnHalfOfPriceWhenFiftyPercentDiscountSupplied(){
        //given
        final int discount = 50;
        final BigDecimal initialPrice = new BigDecimal(11.77);
        final double expected = 5.885;

        //when
        BigDecimal actual = utils.calculatePriceWithDiscount(initialPrice, discount);

        //then
        Assert.assertEquals(expected, actual.doubleValue(), DOUBLE_DELTA);
    }

}