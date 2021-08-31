package com.example.hexagonal.architecture.domain.model;

import java.util.Objects;

public final class Project {

    private final ProjectId projectId;
    private final ProjectName name;

    public Project(ProjectId projectId, ProjectName name) {
        this.projectId = projectId;
        this.name = name;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public ProjectName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(projectId, project.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(projectId);
    }
}
