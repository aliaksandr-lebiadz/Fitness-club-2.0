package com.epam.fitness.dao.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
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

    private Connection connection;
    private Builder<T> builder;

    public AbstractDao(Connection connection, Builder<T> builder){
        this.connection = connection;
        this.builder = builder;
    }

    /**
     * <p>Executes a query as a prepared statement with some parameters.</p>
     *
     * @param query a query to prepare statement
     * @param params parameters for the prepared statement
     * @return a list of entities in the result of the query execution
     */
    protected List<T> executeQuery(String query, Object... params) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(query)){
            setStatementParameters(statement, params);
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while(resultSet.next()){
                T entity = builder.build(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException ex){
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    /**
     * <p>Executes update with the supplied query as a prepared statement
     * with some parameters.</p>
     *
     * @param query a query to prepare statement
     * @param params parameters for the prepared statement
     */
     protected void executeUpdate(String query, Object... params) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(query)){
            setStatementParameters(statement, params);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    /**
     * <p>Finds an entity in the table by id.</p>
     *
     * @param id a id of the entity to find
     * @return an optional of entity when supplied id is present
     * in the table and empty optional otherwise
     */
    @Override
    public Optional<T> findById(int id) throws DaoException{
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
    protected Optional<T> executeForSingleResult(String query, Object... params) throws DaoException{
        List<T> items = executeQuery(query, params);
        if(items.size() > 0){
            T item = items.get(0);
            return Optional.of(item);
        } else{
            return Optional.empty();
        }
    }

    /**
     * <p>Gets all entities from the table, uses the abstract method
     * {@link #getTableName()} to find out the name of the table.</p>
     *
     * @return a list of all entities from the table
     */
    @Override
    public List<T> getAll() throws DaoException{
        String formattedQuery = insertTableName(GET_ALL_QUERY);
        return executeQuery(formattedQuery);
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

    /**
     * <p>Sets parameters to the prepared statement.</p>
     *
     * @param statement a prepared statement
     * @param params parameters to set
     */
    private void setStatementParameters(PreparedStatement statement, Object... params) throws SQLException{
        for(int i = 0; i < params.length; i++){
            statement.setObject(i + 1, params[i]);
        }
    }

}