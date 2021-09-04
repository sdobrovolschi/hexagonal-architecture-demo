package com.example.hexagonal.architecture;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Configuration
class ComponentTestsConfig {

    @Value("${embedded.service.host}")
    String host;

    @Value("${embedded.service.port}")
    int port;

    @Bean
    TestRestTemplate testRestTemplate(RestTemplateBuilder restTemplateBuild) {
        var restTemplate = new TestRestTemplate(restTemplateBuild);

        var handler = new RootUriTemplateHandler("http://%s:%s".formatted(host, port));
        restTemplate.setUriTemplateHandler(handler);

        restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());

        return restTemplate;
    }

    @Bean
    ParameterNamesModule parameterNamesModule() {
        return new ParameterNamesModule(Mode.PROPERTIES);
    }
}
