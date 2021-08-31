package com.example.hexagonal.architecture.domain.model;

import java.util.Optional;

public interface Projects {

    boolean add(Project project);

    Optional<Project> find(ProjectId projectId);
}
