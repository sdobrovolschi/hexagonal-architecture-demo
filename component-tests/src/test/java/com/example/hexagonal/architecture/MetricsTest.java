package com.example.hexagonal.architecture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.function.Predicate;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@ComponentTest
class MetricsTest {

    @Autowired
    TestRestTemplate restTemplate;

//    @LocalManagementPort
    int managementPort;

    @Test
    void httpServerRequestsExposure() {
        restTemplate.getForObject("/test-resource", String.class);

        Predicate<String> counter = metric("http_server_requests_seconds_count")
                .and(tag("method", GET.name()))
                .and(tag("uri", "/test-resource"))
                .and(tag("status", String.valueOf(OK.value())));

        Predicate<String> histogram = metric("http_server_requests_seconds_bucket")
                .and(tag("method", GET.name()))
                .and(tag("uri", "/test-resource"))
                .and(tag("status", String.valueOf(OK.value())));

        assertThat(metrics().lines())
                .anyMatch(counter)
                .anyMatch(histogram);
    }

    String metrics() {
        return restTemplate.getForObject("http://localhost:{port}/metrics", String.class, managementPort);
    }

    Predicate<String> metric(String metricName) {
        return metric -> metric.contains(metricName);
    }

    Predicate<String> tag(String name, String value) {
        return metric -> metric.contains(format("%s=\"%s", name, value));
    }
}
