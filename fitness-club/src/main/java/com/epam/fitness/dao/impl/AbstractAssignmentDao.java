package com.epam.fitness.dao.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractAssignmentDao extends AbstractDao<Assignment> implements AssignmentDao {

    private static final String ASSIGNMENT_TABLE = "assignment";
    private static final String GET_ALL_BY_ORDER_ID_QUERY =
            "SELECT * FROM assignment AS a JOIN exercise AS e ON e.id = a.exercise_id " +
                    "WHERE a.order_id = ?";
    private static final String FIND_BY_ID_QUERY =
            "SELECT a.id, e.id AS exercise_id, order_id, workout_date, amount_of_sets, amount_of_reps, e.name, status " +
                    "FROM assignment AS a " +
                    "JOIN exercise AS e ON a.exercise_id = e.id WHERE a.id = ?";

    public AbstractAssignmentDao(Connection connection, Builder<Assignment> builder){
        super(connection, builder);
    }

    @Override
    public List<Assignment> getAllByOrderId(int orderId) throws DaoException {
        return executeQuery(GET_ALL_BY_ORDER_ID_QUERY, orderId);
    }

    @Override
    public Optional<Assignment> findById(int id) throws DaoException {
        return executeForSingleResult(FIND_BY_ID_QUERY, id);
    }

    @Override
    protected String getTableName() {
        return ASSIGNMENT_TABLE;
    }
}
