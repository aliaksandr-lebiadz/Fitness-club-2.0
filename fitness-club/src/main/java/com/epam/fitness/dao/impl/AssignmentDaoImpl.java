package com.epam.fitness.dao.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.exception.DaoException;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * <p>An implementation of the assignment dao interface to provide
 * an access to the assignment entity in the MySql database.</p>
 *
 * @see Assignment
 */
public class AssignmentDaoImpl extends AbstractDao<Assignment> implements AssignmentDao {

    private static final String ASSIGNMENT_TABLE = "assignment";
    private static final String GET_ALL_BY_ORDER_ID_QUERY =
            "SELECT * FROM assignment AS a JOIN exercise AS e ON e.id = a.exercise_id " +
                    "WHERE a.order_id = ?";
    private static final String FIND_BY_ID_QUERY =
            "SELECT a.id, e.id AS exercise_id, order_id, workout_date, amount_of_sets, amount_of_reps, e.name, status " +
                    "FROM assignment AS a " +
                    "JOIN exercise AS e ON a.exercise_id = e.id WHERE a.id = ?";
    private static final String SAVE_ASSIGNMENT_QUERY = "INSERT INTO assignment" +
                    "(id, order_id, exercise_id, amount_of_sets, amount_of_reps, workout_date, status) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "id = VALUES(id)," +
                    "order_id = VALUES(order_id)," +
                    "exercise_id = VALUES(exercise_id)," +
                    "amount_of_sets = VALUES(amount_of_sets)," +
                    "amount_of_reps = VALUES(amount_of_reps)," +
                    "workout_date = VALUES(workout_date)," +
                    "status = VALUES(status)";

    public AssignmentDaoImpl(Connection connection, Builder<Assignment> builder){
        super(connection, builder);
    }

    /**
     * <p>Inserts the assignment into the assignments table when
     * no assignment with the supplied id exists in the table and
     * updates the assignment otherwise.</p>
     *
     * @param assignment the assignment to save
     */
    @Override
    public void save(Assignment assignment) throws DaoException{
        Exercise exercise = assignment.getExercise();
        AssignmentStatus status = assignment.getStatus();
        Object[] fields = {
                assignment.getId(),
                assignment.getOrderId(),
                exercise.getId(),
                assignment.getAmountOfSets(),
                assignment.getAmountOfReps(),
                assignment.getWorkoutDate(),
                status.toString()
        };
        executeUpdate(SAVE_ASSIGNMENT_QUERY, fields);
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