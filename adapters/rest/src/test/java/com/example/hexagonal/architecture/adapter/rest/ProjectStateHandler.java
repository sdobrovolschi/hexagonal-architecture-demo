package com.example.hexagonal.architecture.adapter.rest;

import au.com.dius.pact.provider.junitsupport.State;
import com.example.hexagonal.architecture.application.CreateProject;
import com.example.hexagonal.architecture.application.CreateProjectUseCase;
import com.example.hexagonal.architecture.application.DeleteProjectUseCase;
import com.example.hexagonal.architecture.application.FindProjectUseCase;
import com.example.hexagonal.architecture.application.FindProjectsUseCase;
import com.example.hexagonal.architecture.application.ProjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Component
class ProjectStateHandler implements StateHandler {

    @Autowired
    Environment environment;

    @Autowired
    CreateProjectUseCase createProjectUseCase;

    @Autowired
    FindProjectUseCase findProjectUseCase;

    @Autowired
    FindProjectsUseCase findProjectsUseCase;

    @Autowired
    DeleteProjectUseCase deleteProjectUseCase;

    @State("no project named Test Project")
    Map<String, Object> noProjectNamed() {
        var projectId = randomUUID();
        var name = randomAlphanumeric(5);

        var command = new CreateProject(name);

        when(createProjectUseCase.createProject(refEq(command))).thenReturn(projectId);

        return Map.ofEntries(
                entry("port", environment.getProperty("local.server.port", Integer.class, 8080)),
                entry("projectId", projectId),
                entry("name", name)
        );
    }

    @State("a collection of projects")
    Map<String, Object> aCollectionOfProjects() {
        var project1 = new ProjectInfo(randomUUID(), randomAlphanumeric(5));
        var project2 = new ProjectInfo(randomUUID(), randomAlphanumeric(5));

        when(findProjectsUseCase.findProjects()).thenReturn(List.of(project1, project2));

        return Map.ofEntries(
                entry("projectId1", project1.getProjectId()),
                entry("name1", project1.getName()),
                entry("projectId2", project2.getProjectId()),
                entry("name2", project2.getName())
        );
    }

    @State("a project")
    Map<String, Object> aProjectToBeFound() {
        var projectId = randomUUID();
        var name = randomAlphanumeric(5);

        when(findProjectUseCase.findProject(projectId))
                .thenReturn(Optional.of(new ProjectInfo(projectId, name)));

        return Map.ofEntries(
                entry("projectId", projectId),
                entry("name", name)
        );
    }

    @State("a project")
    Map<String, Object> aProjectToBeDeleted() {
        var projectId = randomUUID();

        doNothing().when(deleteProjectUseCase).deleteProject(projectId);

        return Map.of("projectId", projectId);
    }
}
