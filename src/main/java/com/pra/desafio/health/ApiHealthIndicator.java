package com.pra.desafio.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Custom health indicator to check API availability.
 * This indicator will be included in the /actuator/health endpoint.
 */
@Component
public class ApiHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            // Simple check to verify API is running
            return Health.up()
                    .withDetail("api", "Desafio API")
                    .withDetail("status", "Available")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("api", "Desafio API")
                    .withDetail("status", "Unavailable")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}