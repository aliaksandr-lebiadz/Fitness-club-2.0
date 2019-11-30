package com.epam.fitness.pool.config;

/**
 * <p>Keeps a configuration of the database.</p>
 *
 * @author Alexandr Lebed
 */
public class DatabaseConfig {

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    private int maxWaitInSeconds;

    /**
     * @param driverName a JDBC driver's name
     * @param url a database url
     * @param user a user of the database
     * @param password a user's password
     * @param maxWaitInSeconds a maximal waiting in seconds of the connection
     *                         from the connection pool
     * @param poolSize a connection pool size
     */
    public DatabaseConfig(String driverName, String url, String user, String password,
                          int maxWaitInSeconds, int poolSize) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxWaitInSeconds = maxWaitInSeconds;
        this.poolSize = poolSize;
    }

    public String getDriverName(){
        return driverName;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getMaxWaitInSeconds() {
        return maxWaitInSeconds;
    }
}