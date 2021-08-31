package com.example.hexagonal.architecture.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
class TestServiceConfig {

    @RestController
    static class TestResource {

        @GetMapping(path = "/test-resource", produces = MediaType.TEXT_HTML_VALUE)
        String get(String param) {
            return param;
        }
    }
}
