package com.epam.fitness.dao.factory.impl;

import com.epam.fitness.builder.impl.*;
import com.epam.fitness.dao.api.*;
import com.epam.fitness.dao.impl.postgresql.PostgreSqlAssignmentDao;
import com.epam.fitness.dao.impl.postgresql.PostgreSqlOrderDao;
import com.epam.fitness.dao.impl.postgresql.PostgreSqlUserDao;

import java.sql.Connection;

/**
 * </p>An implementation of the dao factory specified
 * in the creation of PostgreSql-oriented dao classes.</p>
 */
public class PostgreSqlDaoFactory extends AbstractDaoFactory {

    public PostgreSqlDaoFactory(Connection connection) {
        super(connection);
    }

    public UserDao createUserDao() {
        return new PostgreSqlUserDao(getConnection(), new UserBuilder());
    }

    public OrderDao createOrderDao() {
        return new PostgreSqlOrderDao(getConnection(), new OrderBuilder());
    }

    public AssignmentDao createAssignmentDao() {
        return new PostgreSqlAssignmentDao(getConnection(), new AssignmentBuilder());
    }

}