package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Project;
import com.example.hexagonal.architecture.domain.model.ProjectId;
import com.example.hexagonal.architecture.domain.model.ProjectName;
import com.example.hexagonal.architecture.domain.model.Projects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
class CreateProjectServiceTest {

    @Mock
    Projects projects;

    CreateProjectUseCase createProjectUseCase;

    @Captor
    ArgumentCaptor<Project> projectArgumentCaptor;

    @BeforeEach
    void setUp() {
        createProjectUseCase = CreateProjectUseCase.defaultService(projects);
    }

    @Test
    void creation() {
        when(projects.add(any(Project.class))).thenReturn(true);

        var name = randomAlphanumeric(5);
        var command = new CreateProject(name);

        var projectId = createProjectUseCase.createProject(command);

        assertThat(projectId)
                .isInstanceOf(UUID.class);

        verify(projects).add(projectArgumentCaptor.capture());

        assertThat(projectArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new Project(
                        ProjectId.valueOf(projectId),
                        ProjectName.valueOf(command.getName())
                ));
    }
}
