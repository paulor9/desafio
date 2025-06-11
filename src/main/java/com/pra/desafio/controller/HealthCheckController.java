package com.pra.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller for health check endpoints.
 * Provides simple endpoints for checking the health of the application.
 */
@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "Endpoints for checking application health")
public class HealthCheckController {

    private final HealthEndpoint healthEndpoint;

    @Autowired
    public HealthCheckController(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    /**
     * Simple health check endpoint that returns 200 OK if the application is running.
     * This can be used for basic liveness probes.
     *
     * @return 200 OK response
     */
    @GetMapping("/liveness")
    @Operation(summary = "Liveness check", description = "Returns 200 OK if the application is running")
    public ResponseEntity<String> livenessCheck() {
        return ResponseEntity.ok("Service is up and running");
    }

    /**
     * Readiness check endpoint that returns the full health status.
     * This can be used for readiness probes to check if the application is ready to accept traffic.
     *
     * @return Health status
     */
    @GetMapping("/readiness")
    @Operation(summary = "Readiness check", description = "Returns the full health status including database connectivity")
    public ResponseEntity<HealthComponent> readinessCheck() {
        HealthComponent health = healthEndpoint.health();
        return ResponseEntity.ok(health);
    }
}