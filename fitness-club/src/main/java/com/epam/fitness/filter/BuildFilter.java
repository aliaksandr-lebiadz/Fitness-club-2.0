package com.epam.fitness.filter;

import com.epam.fitness.utils.PropertiesUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Properties;

@WebFilter(filterName = "buildFilter", urlPatterns = {"/*"})
public class BuildFilter extends AbstractFilter {

    private static final String MAVEN_BUILD_PROPERTIES_FILE = "maven_build.properties";
    private static final String VERSION_PROPERTY = "version";
    private static final String TIMESTAMP_PROPERTY = "timestamp";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Properties properties = PropertiesUtils.getProperties(MAVEN_BUILD_PROPERTIES_FILE);
        String version = properties.getProperty(VERSION_PROPERTY);
        String timestamp = properties.getProperty(TIMESTAMP_PROPERTY);
        request.setAttribute(VERSION_PROPERTY, version);
        request.setAttribute(TIMESTAMP_PROPERTY, timestamp);
        chain.doFilter(request, response);
    }
}
