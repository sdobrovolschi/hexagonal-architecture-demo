package com.example.hexagonal.architecture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.AvailableTag;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.MetricResponse;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@ComponentTest
class MetricsTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Value("${embedded.service.host}")
    String host;

    @Value("${embedded.service.management.port}")
    int managementPort;

    @Test
    void httpServerRequestsExposure() {
        restTemplate.getForObject("/projects", String.class);

        var metrics = metrics("http.server.requests");

        assertThat(metrics)
                .extracting(MetricResponse::getAvailableTags)
                .asInstanceOf(list(AvailableTag.class))
                .extracting(AvailableTag::getTag, AvailableTag::getValues)
                .contains(
                        tuple("method", Set.of(GET.name())),
                        tuple("uri", Set.of("/projects")),
                        tuple("status", Set.of(String.valueOf(OK.value())))
                );
    }

    MetricResponse metrics(String metricName) {
        return restTemplate.getForObject("http://{host}:{port}/metrics/{metricName}", MetricResponse.class,
                host, managementPort, metricName);
    }
}
