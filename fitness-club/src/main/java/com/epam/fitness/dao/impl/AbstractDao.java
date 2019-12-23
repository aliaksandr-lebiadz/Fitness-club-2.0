package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.entity.Identifiable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

/**
 * <p>An abstract class with some implemented methods from the
 * basic dao interface and some useful methods for other dao classes.</p>
 *
 * @param <T> a class, which implements {@link Identifiable} interface
 * @see Identifiable
 * @see Dao
 */
public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM %s WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM %s";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<T> rowMapper;

    public AbstractDao(JdbcTemplate jdbcTemplate, RowMapper<T> rowMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    /**
     * <p>Executes a query as a prepared statement with some parameters.</p>
     *
     * @param query a query to prepare statement
     * @param params parameters for the prepared statement
     * @return a list of entities in the result of the query execution
     */
    protected List<T> executeQuery(String query, Object... params){
        return jdbcTemplate.query(query, params, rowMapper);
    }

    /**
     * <p>Executes update with the supplied query as a prepared statement
     * with some parameters.</p>
     *
     * @param query a query to prepare statement
     * @param params parameters for the prepared statement
     */
    protected void executeUpdate(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }

    /**
     * <p>Finds an entity in the table by id.</p>
     *
     * @param id a id of the entity to find
     * @return an optional of entity when supplied id is present
     * in the table and empty optional otherwise
     */
    @Override
    public Optional<T> findById(int id) {
        String formattedQuery = insertTableName(FIND_BY_ID_QUERY);
        return executeForSingleResult(formattedQuery, id);
    }

    /**
     * <p>Executes a query as the prepared statement and
     * returns only the first element of the result list.</p>
     *
     * @param query query to prepare statement
     * @param params parameters for prepared statement
     * @return optional of the entity when size of the result list
     * greater than zero and empty optional otherwise
     */
    protected Optional<T> executeForSingleResult(String query, Object... params) {
        List<T> items = executeQuery(query, params);
        if(isNotEmpty(items)){
            T item = items.get(0);
            return Optional.of(item);
        } else{
            return Optional.empty();
        }
    }

    @Override
    public List<T> getAll(){
        String formattedQuery = insertTableName(GET_ALL_QUERY);
        return executeQuery(formattedQuery);
    }

    @Override
    public void save(T entity){
        String saveQuery = getSaveQuery();
        Object[] fields = getFields(entity);
        executeUpdate(saveQuery, fields);
    }

    @Override
    public void deleteById(int id){
        throw new UnsupportedOperationException();
    }

    /**
     * <p>Used to get a table name of the specific dao.</p>
     *
     * @return the table name
     */
    protected abstract String getTableName();

    protected abstract String getSaveQuery();

    protected abstract Object[] getFields(T entity);

    /**
     * <p>Formats the supplied query inserting the table name into it.</p>
     *
     * @param query a query to insert
     * @return the query with the inserted table name
     */
    private String insertTableName(String query){
        String tableName = getTableName();
        return String.format(query, tableName);
    }

    private boolean isNotEmpty(List<T> items){
        return !items.isEmpty();
    }

}