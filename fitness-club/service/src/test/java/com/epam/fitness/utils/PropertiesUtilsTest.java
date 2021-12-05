package com.epam.fitness.utils;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class PropertiesUtilsTest {

    private static final String EXISTENT_FILE_NAME = "example.properties";
    private static final String NONEXISTENT_FILE_NAME = "no.properties";


    @Test(expected = IllegalArgumentException.class)
    public void getProperties_withNonexistentFile_illegalArgumentException() throws IOException  {
        //given

        //when
        PropertiesUtils.getProperties(NONEXISTENT_FILE_NAME);

        //then
    }

    @Test
    public void getProperties_withExistentFile_propertiesObject() throws IOException  {
        //given
        final String key = "property-example";
        final String expectedValue = "some property";

        //when
        Properties actual = PropertiesUtils.getProperties(EXISTENT_FILE_NAME);

        //then
        assertEquals(expectedValue, actual.get(key));
    }

}