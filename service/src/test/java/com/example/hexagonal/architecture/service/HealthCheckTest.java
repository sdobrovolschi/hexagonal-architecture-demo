package com.example.hexagonal.architecture.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ComponentTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthCheckTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ApplicationContext applicationContext;

    @LocalManagementPort
    int managementPort;

    @Test
    @Order(1)
    void exposure() {
        var status = restTemplate.getForObject("http://localhost:{port}/status", String.class, managementPort);

        assertThat(status)
                .isEqualTo("{\"status\":\"UP\",\"groups\":[\"liveness\",\"readiness\"]}");
    }

    @ParameterizedTest
    @ValueSource(strings = {"liveness", "readiness"})
    @Order(2)
    void exposureProbes(String group) {
        var response = restTemplate.getForEntity("http://localhost:{port}/status/{group}", String.class, managementPort, group);

        assertExpectedResponse(response, HttpStatus.OK, "{\"status\":\"UP\"}");
    }

    @Test
    @Order(3)
    void readinessProbeFailure() {
        AvailabilityChangeEvent.publish(applicationContext, ReadinessState.REFUSING_TRAFFIC);

        var response = restTemplate.getForEntity("http://localhost:{port}/status/readiness", String.class, managementPort);

        assertExpectedResponse(response, HttpStatus.SERVICE_UNAVAILABLE, "{\"status\":\"OUT_OF_SERVICE\"}");
    }

    @Test
    @Order(4)
    void livenessProbeFailure() {
        AvailabilityChangeEvent.publish(applicationContext, LivenessState.BROKEN);

        var response = restTemplate.getForEntity("http://localhost:{port}/status/liveness", String.class, managementPort);

        assertExpectedResponse(response, HttpStatus.SERVICE_UNAVAILABLE, "{\"status\":\"DOWN\"}");
    }

    private static void assertExpectedResponse(ResponseEntity<String> response, HttpStatus expectedStatus, String expectedBody) {
        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("check status code")
                    .isEqualTo(expectedStatus);

            softly.assertThat(response.getBody())
                    .as("check body")
                    .isEqualTo(expectedBody);
        });
    }
}
