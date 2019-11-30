package com.epam.fitness.validator;

import com.epam.fitness.validator.api.PaymentValidator;
import com.epam.fitness.validator.impl.PaymentValidatorImpl;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class PaymentValidatorImplTest {

    private static final String VALID_CARD_NUMBER = "1241351910126701";
    private static final String INVALID_CARD_NUMBER_WITH_SHORTER_LENGTH = "1211355910178";
    private static final String INVALID_CARD_NUMBER_WITH_LONGER_LENGTH = "121135591017812412412";
    private static final String INVALID_CARD_NUMBER_WITH_LETTERS = "g241351s1012670c";
    private static final String INVALID_CARD_NUMBER_WITH_SIGNS = "!2413.1.101%670?";

    private static final String VALID_EXPIRATION_DATE = "11/19";
    private static final String INVALID_EXPIRATION_DATE_BY_NUMBERS = "13/12";
    private static final String INVALID_EXPIRATION_DATE_BY_FORMAT = "11.18";
    private static final String INVALID_EXPIRATION_DATE_BY_LENGTH = "13/121";

    private static final String VALID_CVV = "123";
    private static final String INVALID_CVV_WITH_SHORTER_LENGTH = "89";
    private static final String INVALID_CVV_WITH_LONGER_LENGTH = "0912";
    private static final String INVALID_CVV_WITH_LETTERS = "8c1";
    private static final String INVALID_CVV_WITH_SIGNS = "!72";

    private PaymentValidator validator = new PaymentValidatorImpl();

    @DataProvider
    public static Object[][] invalidCardNumberDataProvider(){
        return new Object[][]{
                { INVALID_CARD_NUMBER_WITH_SHORTER_LENGTH },
                { INVALID_CARD_NUMBER_WITH_LONGER_LENGTH },
                { INVALID_CARD_NUMBER_WITH_LETTERS },
                { INVALID_CARD_NUMBER_WITH_SIGNS }
        };
    }

    @DataProvider
    public static Object[][] invalidExpirationDateDataProvider(){
        return new Object[][]{
                { INVALID_EXPIRATION_DATE_BY_NUMBERS },
                { INVALID_EXPIRATION_DATE_BY_FORMAT },
                { INVALID_EXPIRATION_DATE_BY_LENGTH }
        };
    }

    @DataProvider
    public static Object[][] invalidCvvDataProvider(){
        return new Object[][]{
                { INVALID_CVV_WITH_SHORTER_LENGTH },
                { INVALID_CVV_WITH_LONGER_LENGTH },
                { INVALID_CVV_WITH_LETTERS },
                { INVALID_CVV_WITH_SIGNS }
        };
    }

    @Test
    public void testIsCardNumberValidShouldReturnTrueWhenValidCardNumberSupplied() {
        //given

        //when
        boolean actual = validator.isCardNumberValid(VALID_CARD_NUMBER);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    @UseDataProvider("invalidCardNumberDataProvider")
    public void testIsCardNumberValidShouldReturnFalseWhenInvalidCardNumberSupplied(String cardNumber){
        //given

        //when
        boolean actual =validator.isCardNumberValid(cardNumber);

        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsExpirationDateValidShouldReturnTrueWhenValidExpirationDateSupplied(){
        //given

        //when
        boolean actual = validator.isExpirationDateValid(VALID_EXPIRATION_DATE);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    @UseDataProvider("invalidExpirationDateDataProvider")
    public void testIsExpirationDateValidShouldReturnFalseWhenInvalidExpirationDateSupplied(String thru){
        //given

        //when
        boolean actual =validator.isExpirationDateValid(thru);

        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsCvvValidShouldReturnTrueWhenValidCvvSupplied(){
        //given

        //when
        boolean actual = validator.isCvvValid(VALID_CVV);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    @UseDataProvider("invalidCvvDataProvider")
    public void testIsCvvValidShouldReturnFalseWhenInvalidCvvSupplied(String cvv){
        //given

        //when
        boolean actual =validator.isCvvValid(cvv);

        //then
        Assert.assertFalse(actual);
    }
}