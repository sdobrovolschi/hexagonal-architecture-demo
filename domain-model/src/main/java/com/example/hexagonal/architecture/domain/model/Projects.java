package com.example.hexagonal.architecture.domain.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Projects {

    boolean add(Project project);

    List<Project> findAll();

    Optional<Project> find(ProjectId projectId);

    void delete(UUID projectId);
}
