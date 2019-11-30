package com.epam.fitness.dao.api;

import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>A basic interface for all dao classes.</p>
 *
 * @param <T> a class, which implements {@link Identifiable} interface
 * @see Identifiable
 */
public interface Dao<T extends Identifiable> {


    /**
     * <p>Gets all entities.</p>
     *
     * @return list of entities
     */
    List<T> getAll() throws DaoException;

    /**
     * <p>Inserts an entity when it doesn't exist
     * and updates all fields of the entity otherwise.</p>
     *
     * @param entity entity to save
     */
    void save(T entity) throws DaoException;

    /**
     * <p>Deletes an entity by id.</p>
     *
     * @param id id of the entity to delete
     */
    void deleteById(int id) throws DaoException;

    /**
     * <p>Finds an entity by id.</p>
     *
     * @param id id of the entity to find
     * @return optional of the entity when entity with the
     * supplied id exists and empty optional otherwise
     */
    Optional<T> findById(int id) throws DaoException;

}