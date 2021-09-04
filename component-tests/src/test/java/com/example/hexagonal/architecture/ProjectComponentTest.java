package com.example.hexagonal.architecture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;
import java.util.UUID;

import static java.util.UUID.fromString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@ComponentTest
class ProjectComponentTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void creation() {
        var projectId = createProject();

        var project = getProject(projectId);

        assertThat(project)
                .usingRecursiveComparison()
                .isEqualTo(new ProjectInfo(projectId, "Test Project"));
    }

    UUID createProject() {
        var request = post("/projects")
                .contentType(APPLICATION_JSON)
                .body(new CreateProject("Test Project"));

        var response = restTemplate.exchange(request, Void.class);

        assertThat(response.getStatusCode())
                .as("check HTTP status")
                .isEqualTo(CREATED);

        return extractProjectId(response.getHeaders().getLocation());
    }

    UUID extractProjectId(URI location) {
        var auditId = fromUri(location).build().getPathSegments().get(1);

        return fromString(auditId);
    }

    ProjectInfo getProject(UUID projectId) {
        var response = restTemplate.getForEntity("/projects/{projectId}", ProjectInfo.class, projectId);

        assertThat(response.getStatusCode())
                .as("check HTTP status")
                .isEqualTo(OK);

        return response.getBody();
    }
}
