package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.ProjectId;
import com.example.hexagonal.architecture.domain.model.Projects;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

final class FindProjectService implements FindProjectUseCase {

    private final Projects projects;

    FindProjectService(Projects projects) {
        this.projects = requireNonNull(projects, "The projects must not be null.");
    }

    @Override
    public Optional<ProjectInfo> findProject(UUID projectId) {
        return projects.find(ProjectId.valueOf(projectId))
                .map(project -> new ProjectInfo(
                        project.getProjectId().toUUID(),
                        project.getName().toString()
                ));
    }
}
