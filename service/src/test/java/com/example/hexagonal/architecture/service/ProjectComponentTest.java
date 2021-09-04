package com.example.hexagonal.architecture.service;

import com.example.hexagonal.architecture.application.CreateProject;
import com.example.hexagonal.architecture.application.ProjectInfo;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.io.File;
import java.net.URI;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@ComponentTest //TODO run the service in a docker container
class ProjectComponentTest {

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    static void setUp() throws Exception {
//        https://www.jetbrains.com/help/idea/maven-support.html#maven2_install

//        https://youtrack.jetbrains.com/issue/IDEA-258757

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("../pom.xml"));
        request.setGoals(singletonList("clean package -DskipTests=true"));

        Invoker invoker = new DefaultInvoker();
        invoker.execute(request);
    }

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

        var response = restTemplate.getRestTemplate().exchange(request, Void.class);

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
