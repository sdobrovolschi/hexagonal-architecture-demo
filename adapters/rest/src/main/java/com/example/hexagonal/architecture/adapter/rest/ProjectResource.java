package com.example.hexagonal.architecture.adapter.rest;

import com.example.hexagonal.architecture.application.CreateProject;
import com.example.hexagonal.architecture.application.CreateProjectUseCase;
import com.example.hexagonal.architecture.application.FindProjectUseCase;
import com.example.hexagonal.architecture.application.ProjectInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
final class ProjectResource {

    private final CreateProjectUseCase createProjectUseCase;
    private final FindProjectUseCase findProjectUseCase;

    @PostMapping(path = "/projects", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<?> createProject(@RequestBody CreateProject request) {
        var projectId = createProjectUseCase.createProject(request);

        return created(fromCurrentRequest().path("/{projectId}").build(projectId)).build();
    }

    @GetMapping(path = "/projects/{projectId}", produces = APPLICATION_JSON_VALUE)
    Optional<ProjectInfo> findProject(@PathVariable("projectId") UUID projectId) {
        return findProjectUseCase.findProject(projectId);
    }
}
