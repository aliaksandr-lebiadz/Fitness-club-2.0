package com.epam.fitness.utils;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(DataProviderRunner.class)
public class OrderUtilsTest {

    @DataProvider
    public static Object[][] dataProvider() {
        return new Object[][] {
                { BigDecimal.ZERO, 56, BigDecimal.ZERO },
                { BigDecimal.valueOf(52.23), 100, BigDecimal.valueOf(0) },
                { BigDecimal.valueOf(15.2), 0, BigDecimal.valueOf(15.2) },
                { BigDecimal.valueOf(11.22), 50, BigDecimal.valueOf(5.61) }
        };
    }

    private final OrderUtils utils = new OrderUtils();

    @Test
    @UseDataProvider("dataProvider")
    public void calculatePriceWithDiscount_calculationParams_priceWithDiscount(BigDecimal initialPrice, int discount, BigDecimal expectedPriceWithDiscount){
        //given

        //when
        BigDecimal actualPriceWithDiscount = utils.calculatePriceWithDiscount(initialPrice, discount);

        //then
        assertThat(actualPriceWithDiscount, Matchers.comparesEqualTo(expectedPriceWithDiscount));
    }

}