package com.epam.fitness.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "localeFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {

    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");
    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if(session.getAttribute(LOCALE_ATTRIBUTE) == null){
            session.setAttribute(LOCALE_ATTRIBUTE, DEFAULT_LOCALE);
        }
        chain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {}
}