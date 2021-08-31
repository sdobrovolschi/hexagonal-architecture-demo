package com.example.hexagonal.architecture.config;

import com.example.hexagonal.architecture.application.CreateProjectUseCase;
import com.example.hexagonal.architecture.application.FindProjectUseCase;
import com.example.hexagonal.architecture.domain.model.Projects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ServiceConfig {

    @Bean
    CreateProjectUseCase createProjectUseCase(Projects projects) {
        return CreateProjectUseCase.defaultService(projects);
    }

    @Bean
    FindProjectUseCase findProjectUseCase(Projects projects) {
        return FindProjectUseCase.defaultService(projects);
    }
}
