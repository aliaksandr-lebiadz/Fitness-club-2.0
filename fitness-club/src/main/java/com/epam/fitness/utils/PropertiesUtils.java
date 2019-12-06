package com.epam.fitness.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private PropertiesUtils(){
        //private constructor
    }

    /**
     * <p>Creates a {@link Properties} instance from the supplied properties file.</p>
     *
     * @param propertiesFileName a name of the properties file
     * @return a {@link Properties} instance
     * @throws IllegalArgumentException when the supplied properties file isn't
     * found in the properties directory
     */
    public static Properties getProperties(String propertiesFileName) throws IOException {
        ClassLoader loader = PropertiesUtils.class.getClassLoader();
        try(InputStream inputStream = loader.getResourceAsStream(propertiesFileName)){
            if(inputStream != null){
                Properties properties = new Properties();
                properties.load(inputStream);
                return properties;
            } else {
                throw new IllegalArgumentException("Properties file is not found!");
            }
        }
    }

}
