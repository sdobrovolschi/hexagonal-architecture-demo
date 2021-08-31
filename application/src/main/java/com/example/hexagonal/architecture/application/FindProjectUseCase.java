package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Projects;

import java.util.Optional;
import java.util.UUID;

public interface FindProjectUseCase {

    static FindProjectUseCase defaultService(Projects projects) {
        return new FindProjectService(projects);
    }

    Optional<ProjectInfo> findProject(UUID projectId);
}
