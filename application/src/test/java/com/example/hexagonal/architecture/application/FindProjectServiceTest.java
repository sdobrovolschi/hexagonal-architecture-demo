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

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

@UnitTest
class FindProjectServiceTest {

    @Mock
    Projects projects;

    FindProjectUseCase findProjectUseCase;

    @BeforeEach
    void setUp() {
        findProjectUseCase = FindProjectUseCase.defaultService(projects);
    }

    @ParameterizedTest
    @MethodSource("findingArgumentsProvider")
    void finding(ProjectId projectId, Project project) {
        when(projects.find(projectId)).thenReturn(Optional.ofNullable(project));

        var result = findProjectUseCase.findProject(projectId.toUUID());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(Optional.ofNullable(project).
                        map(p -> new ProjectInfo(
                                p.getProjectId().toUUID(),
                                p.getName().toString())));
    }

    static Stream<Arguments> findingArgumentsProvider() {
        var projectId = ProjectId.valueOf(randomUUID());

        return Stream.of(
                arguments(projectId, new Project(projectId, ProjectName.valueOf(randomAlphanumeric(5)))),
                arguments(projectId, null)
        );
    }
}
