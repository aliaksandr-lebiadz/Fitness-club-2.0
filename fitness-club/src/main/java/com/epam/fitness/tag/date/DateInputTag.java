package com.epam.fitness.tag.date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateInputTag extends TagSupport {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String HTML_DATE_TAG =
            "<input type=\"date\" name=\"%s\" min=\"%s\" value=\"%s\" required/>";

    private String name;
    private DateAttribute minDate;

    public void setName(String name){
        this.name = name;
    }

    public void setMinDate(String minDateValue){
        this.minDate = DateAttribute.valueOf(minDateValue.toUpperCase());
    }

    @Override
    public int doStartTag() throws JspException {
        String date = getMinimumDateValue(); // min value and default value
        try {
            JspWriter out = pageContext.getOut();
            String inputTag = String.format(HTML_DATE_TAG, name, date, date);
            out.write(inputTag);
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        } // JspWriter doesn't need to be closed
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    private String getMinimumDateValue() throws JspException {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime actual;
        switch (minDate) {
            case TODAY:
                actual = today;
                break;
            case TOMORROW:
                actual = today.plusDays(1);
                break;
            case YESTERDAY:
                actual = today.minusDays(1);
                break;
            default:
                throw new JspException("Invalid minimum date");
        }
        return actual.format(DATE_FORMAT);
    }

}