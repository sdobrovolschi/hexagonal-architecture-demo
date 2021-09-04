package com.example.hexagonal.architecture.adapter.rest;

import com.example.hexagonal.architecture.application.CreateProject;
import com.example.hexagonal.architecture.application.CreateProjectUseCase;
import com.example.hexagonal.architecture.application.DeleteProjectUseCase;
import com.example.hexagonal.architecture.application.FindProjectUseCase;
import com.example.hexagonal.architecture.application.FindProjectsUseCase;
import com.example.hexagonal.architecture.application.ProjectInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
final class ProjectResource {

    private final CreateProjectUseCase createProjectUseCase;
    private final FindProjectsUseCase findProjectsUseCase;
    private final FindProjectUseCase findProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<?> create(@RequestBody CreateProject request) {
        var projectId = createProjectUseCase.createProject(request);

        return created(fromCurrentRequest().path("/{projectId}").build(projectId)).build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    List<ProjectInfo> findAll() {
        return findProjectsUseCase.findProjects();
    }

    @GetMapping(path = "/{projectId}", produces = APPLICATION_JSON_VALUE)
    Optional<ProjectInfo> find(@PathVariable("projectId") UUID projectId) {
        return findProjectUseCase.findProject(projectId);
    }

    @DeleteMapping(path = "/{projectId}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable("projectId") UUID projectId) {
        deleteProjectUseCase.deleteProject(projectId);
    }
}
