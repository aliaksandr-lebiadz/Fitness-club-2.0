package com.epam.fitness.pool;

import com.epam.fitness.pool.config.ConfigFactory;
import com.epam.fitness.pool.config.DatabaseConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <p>It's used to create real connections to the database using
 * the {@link #createConnection()} method and the {@link ConfigFactory}.</p>
 *
 * @author Alexandr Lebed
 * @see ConfigFactory
 * @see DatabaseConfig
 */
public class ConnectionFactory {

    private static final String DATABASE_PROPERTIES_FILE = "database.properties";

    private DatabaseConfig config;

    public ConnectionFactory(){
        try{
            ConfigFactory factory = new ConfigFactory();
            config = factory.getConfig(DATABASE_PROPERTIES_FILE);
            String driverName = config.getDriverName();
            Class.forName(driverName);
        } catch(ClassNotFoundException | IOException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    /**
     * <p>Creates a proxy connection to the database with the help
     * of the parameters of the {@link DatabaseConfig} instance.</p>
     *
     * @return the created proxy connection
     */
    public ProxyConnection createConnection(){
        try {
            String url = config.getUrl();
            String user = config.getUser();
            String password = config.getPassword();
            Connection connection = DriverManager.getConnection(url, user, password);
            return new ProxyConnection(connection);
        } catch(SQLException ex){
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    public int getMaxWaitInSeconds(){
        return config.getMaxWaitInSeconds();
    }

    public int getPoolSize(){
        return config.getPoolSize();
    }

}