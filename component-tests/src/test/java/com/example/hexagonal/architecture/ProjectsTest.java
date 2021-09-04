package com.example.hexagonal.architecture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.fromString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@ComponentTest
@DisplayName("Projects")
class ProjectsTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            var projects = findAll();

            assertThat(projects).isEmpty();
        }

        @Nested
        @DisplayName("after")
        @TestMethodOrder(OrderAnnotation.class)
        @TestInstance(Lifecycle.PER_CLASS)
        class AfterCreating {

            ProjectInfo project;

            @Test
            @DisplayName("a project is created")
            @Order(1)
            void createProject() {
                project = randomProject();
            }

            @Test
            @DisplayName("it is no longer empty")
            @Order(2)
            void isNotEmpty() {
                var projects = findAll();

                assertThat(projects).isNotEmpty();
            }

            @Test
            @DisplayName("returns the project when looked up by project id")
            @Order(3)
            void projectFound() {
                var project = find(this.project.getProjectId());

                assertThat(project)
                        .usingRecursiveComparison()
                        .isEqualTo(this.project);
            }

            @Nested
            @DisplayName("after")
            @TestMethodOrder(OrderAnnotation.class)
            @TestInstance(Lifecycle.PER_CLASS)
            class AfterDeleting {

                @Test
                @DisplayName("the project is deleted")
                @Order(1)
                void deleteProject() {
                    delete(project.getProjectId());
                }

                @Test
                @DisplayName("it is empty again")
                @Order(2)
                void isEmpty() {
                    var projects = findAll();

                    assertThat(projects).isEmpty();
                }
            }
        }
    }

    ProjectInfo randomProject() {
        var requestBody = new CreateProject("Test Project");
        var request = post("/projects")
                .contentType(APPLICATION_JSON)
                .body(requestBody);

        var response = restTemplate.exchange(request, Void.class);

        var projectId = extractProjectId(response.getHeaders().getLocation());

        return new ProjectInfo(projectId, requestBody.getName());
    }

    UUID extractProjectId(URI location) {
        var auditId = fromUri(location).build().getPathSegments().get(1);

        return fromString(auditId);
    }

    List<ProjectInfo> findAll() {
        var request = get("/projects")
                .accept(APPLICATION_JSON)
                .build();

        return restTemplate.exchange(request, new ParameterizedTypeReference<List<ProjectInfo>>() {
        }).getBody();
    }

    ProjectInfo find(UUID projectId) {
        var request = get("/projects/{projectId}", projectId)
                .accept(APPLICATION_JSON)
                .build();

        return restTemplate.exchange(request, ProjectInfo.class).getBody();
    }

    void delete(UUID projectId) {
        restTemplate.delete("/projects/{projectId}", projectId);
    }
}
