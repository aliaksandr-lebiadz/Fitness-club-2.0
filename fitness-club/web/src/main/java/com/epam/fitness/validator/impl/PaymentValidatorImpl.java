package com.epam.fitness.validator.impl;

import com.epam.fitness.utils.DateUtils;
import com.epam.fitness.validator.api.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PaymentValidatorImpl implements PaymentValidator {

    private static final String CARD_NUMBER_REGEXP = "\\d{16}";
    private static final String EXPIRATION_DATE_REGEXP = "((0[1-9])|(1[0-2]))/\\d{2}";
    private static final String CVV_REGEXP = "\\d{3}";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
    private DateUtils dateUtils;

    @Autowired
    public PaymentValidatorImpl(DateUtils dateUtils){
        this.dateUtils = dateUtils;
    }

    @Override
    public boolean isCardNumberValid(String cardNumber){
        //there can be the Luhn algorithm
        return cardNumber.matches(CARD_NUMBER_REGEXP);
    }

    @Override
    public boolean isExpirationDateValid(String expirationDate){
        return expirationDate.matches(EXPIRATION_DATE_REGEXP) && isCardActive(expirationDate);
    }

    @Override
    public boolean isCvvValid(String cvv){
        return cvv.matches(CVV_REGEXP);
    }

    private boolean isCardActive(String expirationDateStr){
        Date expirationDate = formatDate(expirationDateStr);
        Date currentDateWithoutTime = dateUtils.getFirstDayOfCurrentMonth();
        return !expirationDate.before(currentDateWithoutTime);
    }

    private Date formatDate(String dateToFormat){
        try{
            return dateFormat.parse(dateToFormat);
        } catch (ParseException ex){
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}