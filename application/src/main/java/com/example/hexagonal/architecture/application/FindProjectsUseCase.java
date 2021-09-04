package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Projects;

import java.util.List;

public interface FindProjectsUseCase {

    static FindProjectsUseCase defaultService(Projects projects) {
        return new FindProjectsService(projects);
    }

    List<ProjectInfo> findProjects();
}
