package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Projects;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

final class DeleteProjectService implements DeleteProjectUseCase {

    private final Projects projects;

    public DeleteProjectService(Projects projects) {
        this.projects = requireNonNull(projects);
    }

    @Override
    public void deleteProject(UUID projectId) {
        projects.delete(projectId);
    }
}
