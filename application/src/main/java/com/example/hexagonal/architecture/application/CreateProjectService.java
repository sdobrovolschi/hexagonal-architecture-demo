package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Project;
import com.example.hexagonal.architecture.domain.model.ProjectId;
import com.example.hexagonal.architecture.domain.model.ProjectName;
import com.example.hexagonal.architecture.domain.model.Projects;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

final class CreateProjectService implements CreateProjectUseCase {

    private final Projects projects;

    CreateProjectService(Projects projects) {
        this.projects = requireNonNull(projects, "The projects must not be null.");
    }

    @Override
    public UUID createProject(CreateProject command) {
        requireNonNull(command, "The CreateProject command must not be null.");

        var project = new Project(
                ProjectId.newIdentity(),
                ProjectName.valueOf(command.getName())
        );

        if (!projects.add(project)) {
            return null;
        }

        return project.getProjectId().toUUID();
    }
}
