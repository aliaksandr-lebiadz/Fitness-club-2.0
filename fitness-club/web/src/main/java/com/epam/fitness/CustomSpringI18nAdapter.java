package com.epam.fitness;

import org.displaytag.localization.I18nSpringAdapter;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.util.Locale;

public class CustomSpringI18nAdapter extends I18nSpringAdapter {


    @Override
    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext) {
        MessageSource messageSource = getMessageSource(pageContext);
        if (messageSource == null) {
            return null;
        }
        String key = getKey(resourceKey, defaultValue);
        String message = getMessage(messageSource, pageContext, key);
        if (message == null && resourceKey != null) {
            message = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
        }
        return message;

    }

    private MessageSource getMessageSource(PageContext pageContext){
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        ServletContext servletContext = pageContext.getServletContext();
        return RequestContextUtils.findWebApplicationContext(request, servletContext);
    }

    private String getKey(String resourceKey, String defaultValue){
        return resourceKey != null ? resourceKey : defaultValue;
    }

    private String getMessage(MessageSource messageSource, PageContext pageContext, String key){
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Locale locale = RequestContextUtils.getLocale(request);
        return messageSource.getMessage(key, null, null, locale);
    }

}
