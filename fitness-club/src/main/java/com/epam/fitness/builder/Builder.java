package com.epam.fitness.builder;

import com.epam.fitness.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>A generic interface for building an instance of any class,
 * which implements the {@link Identifiable} interface.</p>
 *
 * @param <T> a class, which implements the {@link Identifiable} interface
 * @see Identifiable
 */
public interface Builder<T extends Identifiable> {

    /**
     * <p>Builds any {@link Identifiable} instance from the supplied {@link ResultSet} instance.</p>
     *
     * @param resultSet a result set of parameters
     * @return a built instance
     */
    T build(ResultSet resultSet) throws SQLException;

}