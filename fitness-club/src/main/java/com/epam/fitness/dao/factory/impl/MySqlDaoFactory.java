package com.epam.fitness.dao.factory.impl;

import com.epam.fitness.builder.impl.*;
import com.epam.fitness.dao.api.*;
import com.epam.fitness.dao.impl.mysql.MySqlAssignmentDao;
import com.epam.fitness.dao.impl.mysql.MySqlOrderDao;
import com.epam.fitness.dao.impl.mysql.MySqlUserDao;

import java.sql.Connection;

/**
 * </p>An implementation of the dao factory specified
 * in the creation of MySql-oriented dao classes.</p>
 */
public class MySqlDaoFactory extends AbstractDaoFactory {

    public MySqlDaoFactory(Connection connection) {
        super(connection);
    }

    public UserDao createUserDao() {
        return new MySqlUserDao(getConnection(), new UserBuilder());
    }

    public OrderDao createOrderDao() {
        return new MySqlOrderDao(getConnection(), new OrderBuilder());
    }

    public AssignmentDao createAssignmentDao() {
        return new MySqlAssignmentDao(getConnection(), new AssignmentBuilder());
    }

}