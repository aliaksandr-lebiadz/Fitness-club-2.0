package com.epam.fitness.validator;

import com.epam.fitness.utils.DateUtils;
import com.epam.fitness.validator.api.PaymentValidator;
import com.epam.fitness.validator.impl.PaymentValidatorImpl;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class PaymentValidatorImplTest {

    private static final String VALID_CARD_NUMBER = "1241351910126701";
    private static final String INVALID_CARD_NUMBER_WITH_SHORTER_LENGTH = "1211355910178";
    private static final String INVALID_CARD_NUMBER_WITH_LONGER_LENGTH = "121135591017812412412";
    private static final String INVALID_CARD_NUMBER_WITH_LETTERS = "g241351s1012670c";
    private static final String INVALID_CARD_NUMBER_WITH_SIGNS = "!2413.1.101%670?";

    private static final String NOT_EXPIRED_DATE = "10/19";
    private static final String EXPIRED_DATE = "09/19";
    private static final String INVALID_EXPIRATION_DATE_BY_NUMBERS = "13/12";
    private static final String INVALID_EXPIRATION_DATE_BY_FORMAT = "11.18";
    private static final String INVALID_EXPIRATION_DATE_BY_LENGTH = "13/121";

    private static final String VALID_CVV = "123";
    private static final String INVALID_CVV_WITH_SHORTER_LENGTH = "89";
    private static final String INVALID_CVV_WITH_LONGER_LENGTH = "0912";
    private static final String INVALID_CVV_WITH_LETTERS = "8c1";
    private static final String INVALID_CVV_WITH_SIGNS = "!72";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
    private static final String FIRST_DAY_OF_CURRENT_MONTH = "01/10/19";

    private DateUtils dateUtils = mock(DateUtils.class);
    private PaymentValidator validator = new PaymentValidatorImpl(dateUtils);

    @Before
    public void createMocks() throws ParseException{
        Date firstDayOfCurrentMonth = DATE_FORMAT.parse(FIRST_DAY_OF_CURRENT_MONTH);
        when(dateUtils.getFirstDayOfCurrentMonth()).thenReturn(firstDayOfCurrentMonth);
    }

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
        boolean actual = validator.isCardNumberValid(cardNumber);

        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsExpirationDateValidShouldReturnTrueWhenNotExpiredDateSupplied() {
        //given

        //when
        boolean actual = validator.isExpirationDateValid(NOT_EXPIRED_DATE);

        //then
        Assert.assertTrue(actual);
        verify(dateUtils, times(1)).getFirstDayOfCurrentMonth();
    }

    @Test
    public void testIsExpirationDateValidShouldReturnFalseWhenExpiredDateSupplied() {
        //given

        //when
        boolean actual = validator.isExpirationDateValid(EXPIRED_DATE);

        //then
        Assert.assertFalse(actual);
        verify(dateUtils, times(1)).getFirstDayOfCurrentMonth();
    }

    @Test
    @UseDataProvider("invalidExpirationDateDataProvider")
    public void testIsExpirationDateValidShouldReturnFalseWhenInvalidExpirationDateSupplied(String expirationDate){
        //given

        //when
        boolean actual = validator.isExpirationDateValid(expirationDate);

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
        boolean actual = validator.isCvvValid(cvv);

        //then
        Assert.assertFalse(actual);
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(dateUtils);
    }
}