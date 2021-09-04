package com.example.hexagonal.architecture.adapter.rest;

import com.example.hexagonal.architecture.application.CreateProjectUseCase;
import com.example.hexagonal.architecture.application.DeleteProjectUseCase;
import com.example.hexagonal.architecture.application.FindProjectUseCase;
import com.example.hexagonal.architecture.application.FindProjectsUseCase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TestRestConfig {

    @MockBean
    CreateProjectUseCase createProjectUseCase;

    @MockBean
    FindProjectsUseCase findProjectsUseCase;

    @MockBean
    FindProjectUseCase findProjectUseCase;

    @MockBean
    DeleteProjectUseCase deleteProjectUseCase;
}
