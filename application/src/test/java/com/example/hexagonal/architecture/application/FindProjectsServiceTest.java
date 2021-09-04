package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Project;
import com.example.hexagonal.architecture.domain.model.ProjectId;
import com.example.hexagonal.architecture.domain.model.ProjectName;
import com.example.hexagonal.architecture.domain.model.Projects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

@UnitTest
class FindProjectsServiceTest {

    @Mock
    Projects projects;

    FindProjectsUseCase findProjectsUseCase;

    @BeforeEach
    void setUp() {
        findProjectsUseCase = FindProjectsUseCase.defaultService(projects);
    }

    @ParameterizedTest
    @MethodSource("findingArgumentsProvider")
    void finding(List<Project> projects) {
        when(this.projects.findAll()).thenReturn(projects);

        var result = findProjectsUseCase.findProjects();

        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(projects.stream()
                        .map(p -> new ProjectInfo(
                                p.getProjectId().toUUID(),
                                p.getName().toString()))
                        .collect(toList()));
    }

    static Stream<Arguments> findingArgumentsProvider() {
        var projectId = ProjectId.valueOf(randomUUID());

        return Stream.of(
                arguments(List.of(
                        new Project(projectId, ProjectName.valueOf(randomAlphanumeric(5))),
                        new Project(projectId, ProjectName.valueOf(randomAlphanumeric(5))))),
                arguments(emptyList())
        );
    }
}
