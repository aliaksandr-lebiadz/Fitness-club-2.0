package com.epam.fitness.dao.api;

import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.order.Order;

import java.util.List;

/**
 * <p>An interface specified for an assignment
 * entity to provide an access to it.</p>
 *
 * @see Assignment
 */
public interface AssignmentDao extends Dao<Assignment>{

    /**
     * <p>Gets all assignments by the supplied order.</p>
     *
     * @return list of found assignments
     */
    List<Assignment> getAllByOrder(Order order);

}