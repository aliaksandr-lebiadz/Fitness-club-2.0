package com.epam.fitness.pool;

import com.epam.fitness.exception.ConnectionPoolException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Provides an access to the created connections.
 * Gives an opportunity to reuse connections to the database.</p>
 *
 * @author Alexandr Lebed
 * @see ProxyConnection
 */
public class ConnectionPool {

    private static ConnectionPool instance = new ConnectionPool();
    private static final boolean SEMAPHORE_FAIR = true;
    private static final Lock LOCK = new ReentrantLock();

    private Queue<ProxyConnection> connections = new LinkedList<>();
    private List<ProxyConnection> givenConnections = new ArrayList<>();
    private Semaphore semaphore;
    private int maxWaitInSeconds;

    private ConnectionPool(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        this.maxWaitInSeconds = connectionFactory.getMaxWaitInSeconds();
        int poolSize = connectionFactory.getPoolSize();
        this.semaphore = new Semaphore(poolSize, SEMAPHORE_FAIR);
        for(int i = 0; i < poolSize; i++){
            ProxyConnection proxyConnection = connectionFactory.createConnection();
            connections.add(proxyConnection);
        }
    }

    /**
     * <p>Provides an access to the instance of the singleton {@link ConnectionPool}.</p>
     *
     * @return the instance of the {@link ConnectionPool}
     */
    public static ConnectionPool getInstance(){
        return instance;
    }

    /**
     * <p>Gives a proxy connection from the pool.</p>
     *
     * <p>If there are no available connections in the pool, it's waiting until an
     * available connection appears.</p>
     *
     * @return the proxy connection instance
     * @throws ConnectionPoolException when connection waiting timed out
     */
    public ProxyConnection getConnection() throws ConnectionPoolException{

        try{
            if(semaphore.tryAcquire(maxWaitInSeconds, TimeUnit.SECONDS)){
                LOCK.lock();
                ProxyConnection givenConnection = connections.poll();
                givenConnections.add(givenConnection);
                return givenConnection;
            } else{
                throw new ConnectionPoolException("Over waiting time..");
            }
        } catch (InterruptedException ex){
            throw new ConnectionPoolException(ex.getMessage(), ex);
        } finally{
            LOCK.unlock();
        }
    }

    /**
     * <p>Returns the proxy connection to the pool.</p>
     *
     * @param connection the proxy connection to return
     * @throws ConnectionPoolException when the supplied connection isn't from the pool
     */
    public void releaseConnection(ProxyConnection connection) throws ConnectionPoolException{
        if(givenConnections.contains(connection)){
            connections.add(connection);
            givenConnections.remove(connection);
            semaphore.release();
        } else{
            throw new ConnectionPoolException("Invalid connection supplied!");
        }
    }

    /**
     * <p>Closes the connection pool with all it's connections.</p>
     *
     * @throws ConnectionPoolException when {@link SQLException} has thrown by
     * the {@link ProxyConnection#terminate()} method
     */
    public void close() throws ConnectionPoolException {
        try{
            givenConnections.forEach(ProxyConnection::close);
            for(ProxyConnection connection : connections){
                connection.terminate();
            }
        } catch(SQLException ex){
            throw new ConnectionPoolException(ex.getMessage(), ex);
        }
    }

}
