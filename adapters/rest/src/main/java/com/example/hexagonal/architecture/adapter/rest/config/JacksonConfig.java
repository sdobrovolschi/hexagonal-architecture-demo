package com.example.hexagonal.architecture.adapter.rest.config;

import com.example.hexagonal.architecture.application.CreateProject;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/rest.properties")
class JacksonConfig {

    //TODO customer ObjectMapper for REST

    @Bean
    ParameterNamesModule parameterNamesModule() {
        return new ParameterNamesModule(Mode.PROPERTIES);
    }

    @Bean
    Jackson2ObjectMapperBuilderCustomizer restObjectMapperBuilderCustomizer() {
        return builder -> builder
                .mixIn(CreateProject.class, MixIns.CreateProjectMixIn.class);
    }
}
