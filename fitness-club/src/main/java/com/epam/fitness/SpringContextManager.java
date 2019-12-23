package com.epam.fitness;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContextManager {

    private SpringContextManager() {}

    private static final ConfigurableApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(SpringConfig.class);

    public static ApplicationContext getContext(){
        return APPLICATION_CONTEXT;
    }

    public static void closeApplicationContext(){
        APPLICATION_CONTEXT.close();
    }

}
