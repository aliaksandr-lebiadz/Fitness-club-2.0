package com.epam.fitness;

import com.epam.fitness.exception.ConnectionPoolException;
import com.epam.fitness.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {}

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool pool = ConnectionPool.getInstance();
        try{
            pool.close();
        } catch (ConnectionPoolException ex){
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }
}
