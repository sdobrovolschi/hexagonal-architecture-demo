package com.example.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Map;

import static com.playtika.test.common.utils.ContainerUtils.configureCommonsAndStart;
import static java.util.Map.entry;

@Slf4j
@Configuration
@ConditionalOnExpression("${embedded.containers.enabled:true}")
@Order
@ConditionalOnProperty(
        name = "embedded.service.enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties(ServiceProperties.class)
public class EmbeddedServiceBootstrapConfiguration {

    public EmbeddedServiceBootstrapConfiguration() {
        System.out.println("");
    }

    @Bean
    public Container service(ConfigurableEnvironment environment, ServiceProperties properties) {

        GenericContainer service = new GenericContainer<>(properties.getDockerImage())
                .withExposedPorts(properties.getPort(), properties.getManagementPort())
                .waitingFor(Wait.forHttp("/status")
                        .forPort(properties.getManagementPort())
                        .forStatusCode(200));

        service = configureCommonsAndStart(service, properties, log);

        registerServiceEnvironment(service, environment, properties);

        return service;
    }

    private void registerServiceEnvironment(
            GenericContainer service,
            ConfigurableEnvironment environment,
            ServiceProperties properties) {

        var host = service.getHost();
        var port = service.getMappedPort(properties.getPort());
        var managementPort = service.getMappedPort(properties.getManagementPort());

        Map<String, Object> params = Map.ofEntries(
                entry("embedded.service.host", host),
                entry("embedded.service.port", port),
                entry("embedded.service.management.port", managementPort)
        );

        log.info("Started {}. Connection Details: {}, Connection URI: http://{}:{}",
                properties.getDockerImage(), params, host, port);

        var propertySource = new MapPropertySource("embeddedServiceInfo", params);
        environment.getPropertySources().addFirst(propertySource);
    }
}
