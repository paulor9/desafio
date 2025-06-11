package com.pra.desafio.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for the HealthCheckController.
 */
@ExtendWith(MockitoExtension.class)
public class HealthCheckControllerTests {

    @Mock
    private HealthEndpoint healthEndpoint;

    @InjectMocks
    private HealthCheckController healthCheckController;

    private MockMvc mockMvc;

    @Test
    public void testLivenessCheck() throws Exception {
        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(healthCheckController).build();

        // Test the endpoint
        mockMvc.perform(get("/health/liveness"))
                .andExpect(status().isOk())
                .andExpect(content().string("Service is up and running"));
    }

    @Test
    public void testReadinessCheck() throws Exception {
        // Mock the health endpoint to return UP status
        Health health = Health.up().build();
        when(healthEndpoint.health()).thenReturn(health);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(healthCheckController).build();

        // Test the endpoint
        mockMvc.perform(get("/health/readiness"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"UP\"}"));
    }

    @Test
    public void testReadinessCheckWhenDown() throws Exception {
        // Mock the health endpoint to return DOWN status
        Health health = Health.down().withDetail("database", "Unavailable").build();
        when(healthEndpoint.health()).thenReturn(health);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(healthCheckController).build();

        // Test the endpoint
        mockMvc.perform(get("/health/readiness"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"DOWN\",\"details\":{\"database\":\"Unavailable\"}}"));
    }

    @Test
    public void testLivenessCheckDirectly() {
        // Test the controller method directly
        ResponseEntity<String> response = healthCheckController.livenessCheck();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Service is up and running", response.getBody());
    }
}
