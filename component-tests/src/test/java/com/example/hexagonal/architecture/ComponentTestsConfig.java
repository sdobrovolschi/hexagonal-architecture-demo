package com.example.hexagonal.architecture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ComponentTestsConfig {

    @Value("${embedded.service.host}")
    String host;

    @Value("${embedded.service.port}")
    Integer port;

    @Bean
    TestRestTemplate testRestTemplate() {
        var restTemplateBuilder = new RestTemplateBuilder().rootUri("http://%s:%s".formatted(host, port));
        return new TestRestTemplate(restTemplateBuilder, null, null);
    }
}
