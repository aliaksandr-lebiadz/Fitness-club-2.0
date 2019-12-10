package com.epam.fitness.pool;

import com.epam.fitness.pool.config.ConfigFactory;
import com.epam.fitness.pool.config.DatabaseConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ConfigFactoryTest {

    private static final String EXISTENT_RESOURCE_FILE_NAME = "database_config.properties";
    private static final String NONEXISTENT_RESOURCE_FILE_NAME = "invalid.properties";
    private static final DatabaseConfig EXPECTED_CONFIG = new DatabaseConfig("driver",
            "my_url", "admin", "admin", 1, 10);

    private ConfigFactory configFactory = new ConfigFactory();

    @Test
    public void testGetConfigShouldReturnDatabaseConfigWhenExistentResourceFileSupplied() throws IOException {
        //given

        //when
        DatabaseConfig actual = configFactory.getConfig(EXISTENT_RESOURCE_FILE_NAME);

        //then
        Assert.assertEquals(EXPECTED_CONFIG.getDriverName(), actual.getDriverName());
        Assert.assertEquals(EXPECTED_CONFIG.getUrl(), actual.getUrl());
        Assert.assertEquals(EXPECTED_CONFIG.getUser(), actual.getUser());
        Assert.assertEquals(EXPECTED_CONFIG.getPassword(), actual.getPassword());
        Assert.assertEquals(EXPECTED_CONFIG.getPoolSize(), actual.getPoolSize());
        Assert.assertEquals(EXPECTED_CONFIG.getMaxWaitInSeconds(), actual.getMaxWaitInSeconds());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConfigShouldThrowExceptionWhenNonexistentResourceFileSupplied() throws IOException {
        //given

        //when
        configFactory.getConfig(NONEXISTENT_RESOURCE_FILE_NAME);

        //then
    }

}