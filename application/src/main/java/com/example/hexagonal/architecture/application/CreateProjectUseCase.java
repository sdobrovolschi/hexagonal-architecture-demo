package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Projects;

import java.util.UUID;

public interface CreateProjectUseCase {

    static CreateProjectUseCase defaultService(Projects projects) {
        return new CreateProjectService(projects);
    }

    UUID createProject(CreateProject command);
}
