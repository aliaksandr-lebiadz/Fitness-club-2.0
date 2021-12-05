package com.epam.fitness.dao.api;

import com.epam.fitness.entity.user.User;

import javax.transaction.Transactional;
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
     * <p>Finds users with the given trainer.</p>
     *
     * @return list of clients
     */
    List<User> findClientsOfTrainer(User trainer);

    /**
     * <p>Finds a user with the given email.</p>
     *
     * @param email a user's email
     * @return optional of the user when the user with
     * the given email is found and empty optional otherwise
     */
    Optional<User> findUserByEmail(String email);

    User getRandomTrainer();
}