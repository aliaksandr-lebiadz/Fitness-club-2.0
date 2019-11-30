package com.epam.fitness.tag.localizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public abstract class AbstractLocalizer extends BodyTagSupport {

    private JspWriter writer;

    @Override
    public int doStartTag(){
        writer = pageContext.getOut();
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        String value = bodyContent.getString();
        String attributeName = getAttributeName(value);
        try{
            Object attribute = pageContext.findAttribute(attributeName);
            writer.print(attribute);
        } catch (IOException ex){
            throw new JspException(ex.getMessage(), ex);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    protected abstract String getAttributeName(String value) throws JspException;

}
