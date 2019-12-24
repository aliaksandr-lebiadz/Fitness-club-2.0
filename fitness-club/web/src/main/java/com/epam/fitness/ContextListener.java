package com.epam.fitness;

import com.epam.fitness.utils.PropertiesUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final String MAVEN_BUILD_PROPERTIES_FILE = "maven_build.properties";
    private static final String VERSION_PROPERTY = "version";
    private static final String TIMESTAMP_PROPERTY = "timestamp";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        try{
            setDataToApplicationScope(servletContext);
        } catch (IOException ex){
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SpringContextManager.closeApplicationContext();
    }

    private void setDataToApplicationScope(ServletContext servletContext) throws IOException{
        Properties properties = PropertiesUtils.getProperties(MAVEN_BUILD_PROPERTIES_FILE);
        String version = properties.getProperty(VERSION_PROPERTY);
        String timestamp = properties.getProperty(TIMESTAMP_PROPERTY);
        servletContext.setAttribute(VERSION_PROPERTY, version);
        servletContext.setAttribute(TIMESTAMP_PROPERTY, timestamp);
    }
}
