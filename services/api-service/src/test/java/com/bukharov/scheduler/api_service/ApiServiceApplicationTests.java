package com.bukharov.scheduler.api_service;

import com.bukharov.scheduler.api_service.repository.TaskStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Helper to build a typical job payload
    private String buildJobPayload(String name, Date executionTime) throws Exception {
        Map<String, Object> job = new HashMap<>();
        job.put("name", name);
        job.put("executionTime", executionTime);
        return objectMapper.writeValueAsString(job);
    }

    @Test
    void contextLoads_shouldStartSpringContext() {
        // Basic smoke test; if the context fails to start the test suite will fail here.
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void createTaskTest() throws Exception {
        String jobJson = buildJobPayload("test-task-name", new Date());

        // Create task (accept either 2xx)
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jobJson))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

        // Ensure the job appears in the list endpoint
        mockMvc.perform(get("/tasks")
                .queryParam("status", TaskStatus.SCHEDULED.toString())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", not(empty())))
            .andExpect(jsonPath("$[*].name", hasItem("test-task-name")));
    }

}