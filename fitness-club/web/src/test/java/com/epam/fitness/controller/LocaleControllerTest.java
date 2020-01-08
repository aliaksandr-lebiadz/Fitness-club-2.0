package com.epam.fitness.controller;

import org.junit.Assert;
import org.junit.Test;
import javax.servlet.http.HttpSession;

import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class LocaleControllerTest extends AbstractControllerTest{

    private static final String CHANGE_LOCALE_REQUEST = "/locale/change";
    private static final String LANGUAGE_PARAMETER = "language";
    private static final String COUNTRY_PARAMETER = "country";
    private static final String EXPECTED_LANGUAGE = "en";
    private static final String EXPECTED_COUNTRY = "US";
    private static final Locale EXPECTED_LOCALE = new Locale(EXPECTED_LANGUAGE, EXPECTED_COUNTRY);
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String REFERER_HEADER = "referer";
    private static final String CURRENT_PAGE = "http://localhost:8080/fitness/order/list";

    @Test
    public void testChangeLocale() throws Exception{
        HttpSession session = mockMvc.perform(get(CHANGE_LOCALE_REQUEST)
                .param(LANGUAGE_PARAMETER, EXPECTED_LANGUAGE)
                .param(COUNTRY_PARAMETER, EXPECTED_COUNTRY)
                .header(REFERER_HEADER, CURRENT_PAGE))
                .andExpect(redirectedUrl(CURRENT_PAGE))
                .andReturn()
                .getRequest()
                .getSession();
        Assert.assertNotNull(session);

        Locale actual = (Locale) session.getAttribute(LOCALE_ATTRIBUTE);
        Assert.assertEquals(EXPECTED_LOCALE, actual);
    }

}