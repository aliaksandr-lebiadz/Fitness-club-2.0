package com.epam.fitness.dao.factory.impl;

import com.epam.fitness.builder.impl.*;
import com.epam.fitness.dao.api.*;
import com.epam.fitness.dao.impl.*;
import com.epam.fitness.dao.impl.mysql.MySqlOrderDao;

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
        return new UserDaoImpl(getConnection(), new UserBuilder());
    }

    public OrderDao createOrderDao() {
        return new MySqlOrderDao(getConnection(), new OrderBuilder());
    }

    public AssignmentDao createAssignmentDao() {
        return new AssignmentDaoImpl(getConnection(), new AssignmentBuilder());
    }

}