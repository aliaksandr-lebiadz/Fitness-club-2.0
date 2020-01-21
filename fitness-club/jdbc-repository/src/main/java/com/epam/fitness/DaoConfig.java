package com.epam.fitness;

import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@Lazy
@PropertySource("classpath:database.properties")
public class DaoConfig {

    @Value("${database.name}")
    private String databaseName;

    @Autowired
    private WebApplicationContext context;

    @Bean
    public UserDao userDao(){
        return (UserDao) context.getBean(databaseName + "UserDao");
    }

    @Bean
    public OrderDao orderDao(){
        return (OrderDao) context.getBean(databaseName + "OrderDao");
    }

    @Bean
    public AssignmentDao assignmentDao(){
        return (AssignmentDao) context.getBean(databaseName + "AssignmentDao");
    }

}
