package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Projects;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableList;

final class FindProjectsService implements FindProjectsUseCase {

    private final Projects projects;

    public FindProjectsService(Projects projects) {
        this.projects = requireNonNull(projects, "The projects must not be null.");
    }

    @Override
    public List<ProjectInfo> findProjects() {
        return projects.findAll().stream()
                .map(project -> new ProjectInfo(
                        project.getProjectId().toUUID(),
                        project.getName().toString()
                ))
                .collect(toUnmodifiableList());
    }
}
