package com.epam.fitness.controller;

import com.epam.fitness.config.SpringWebMvcConfig;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebMvcConfig.class})
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    protected <T> String mapToJson(T entity) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entity);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

}
