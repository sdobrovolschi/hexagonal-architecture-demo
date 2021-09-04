package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Projects;

import java.util.UUID;

public interface DeleteProjectUseCase {

    static DeleteProjectUseCase defaultService(Projects projects) {
        return new DeleteProjectService(projects);
    }

    void deleteProject(UUID projectId);
}
