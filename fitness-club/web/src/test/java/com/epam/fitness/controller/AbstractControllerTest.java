package com.epam.fitness.controller;

import com.epam.fitness.config.SpringWebMvcConfig;
import com.epam.fitness.exception.controller.ControllerAdviceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebMvcConfig.class})
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    protected void configureMockMvc(Object controller){
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdviceImpl())
                .build();
    }

    protected <T> String mapToJson(T entity) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entity);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

}
