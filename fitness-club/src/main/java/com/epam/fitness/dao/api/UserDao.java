package com.epam.fitness.dao.api;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>An interface specified for an user entity to
 * provide an access to it.</p>
 *
 * @see User
 */
public interface UserDao extends Dao<User> {

    /**
     * <p>Finds a user with the given email and password.</p>
     *
     * @param email a user's email
     * @param password a user's password
     * @return optional of the user when the user with
     * given email and password is found and empty optional otherwise
     */
    Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException;

    /**
     * <p>Finds users with the given trainer id.</p>
     *
     * @param trainerId trainer's id
     * @return list of users
     */
    List<User> findUsersByTrainerId(long trainerId) throws DaoException;

    /**
     * <p>Finds all clients.</p>
     *
     * @return a list of found clients
     */
    List<User> getAllClients() throws DaoException;
}