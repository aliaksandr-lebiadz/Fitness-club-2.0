package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.dao.api.AssignmentDao;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgreSqlAssignmentDao extends AbstractDao<Assignment> implements AssignmentDao {

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
            "VALUES(COALESCE(?, (SELECT MAX(id) FROM client_order as uid) + 1, 1), ?, ?, ?, ?, ?, ?::status) " +
            "ON CONFLICT(id) DO UPDATE SET " +
            "order_id = EXCLUDED.order_id," +
            "exercise_id = EXCLUDED.exercise_id," +
            "amount_of_sets = EXCLUDED.amount_of_sets," +
            "amount_of_reps = EXCLUDED.amount_of_reps," +
            "workout_date = EXCLUDED.workout_date," +
            "status = EXCLUDED.status::status";

    @Autowired
    public PostgreSqlAssignmentDao(JdbcTemplate jdbcTemplate, RowMapper<Assignment> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public List<Assignment> getAllByOrder(Order order) {
        int orderId = order.getId();
        return executeQuery(GET_ALL_BY_ORDER_ID_QUERY, orderId);
    }

    @Override
    public Optional<Assignment> findById(int id) {
        return executeForSingleResult(FIND_BY_ID_QUERY, id);
    }

    @Override
    protected Object[] getFields(Assignment assignment) {
        Exercise exercise = assignment.getExercise();
        AssignmentStatus status = assignment.getStatus();
        return new Object[]{
                assignment.getId(),
                assignment.getOrderId(),
                exercise.getId(),
                assignment.getAmountOfSets(),
                assignment.getAmountOfReps(),
                convertToSqlDate(assignment.getWorkoutDate()),
                status.toString()
        };
    }

    @Override
    protected String getSaveQuery() {
        return SAVE_ASSIGNMENT_QUERY;
    }

    @Override
    protected String getTableName() {
        return ASSIGNMENT_TABLE;
    }

    private java.sql.Date convertToSqlDate(Date date){
        long time = date.getTime();
        return new java.sql.Date(time);
    }
}
