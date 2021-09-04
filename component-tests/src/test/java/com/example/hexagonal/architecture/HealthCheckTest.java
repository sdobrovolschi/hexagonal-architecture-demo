package com.example.hexagonal.architecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
class HealthCheckTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Value("${embedded.service.management.port}")
    int managementPort;

    @Test
    void exposure() {
        var status = restTemplate.getForObject("http://localhost:{port}/status", String.class, managementPort);

        assertThat(status)
                .isEqualTo("{\"status\":\"UP\",\"groups\":[\"liveness\",\"readiness\"]}");
    }

    @ParameterizedTest
    @ValueSource(strings = {"liveness", "readiness"})
    void exposureProbes(String group) {
        var response = restTemplate.getForEntity("http://localhost:{port}/status/{group}", String.class, managementPort, group);

        assertThat(response)
                .as("check response")
                .extracting(
                        ResponseEntity::getStatusCode,
                        ResponseEntity::getBody)
                .containsExactly(
                        HttpStatus.OK,
                        "{\"status\":\"UP\"}");
    }
}
