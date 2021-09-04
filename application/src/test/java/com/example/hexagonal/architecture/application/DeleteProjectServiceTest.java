package com.example.hexagonal.architecture.application;

import com.example.hexagonal.architecture.domain.model.Projects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.verify;

@UnitTest
class DeleteProjectServiceTest {

    @Mock
    Projects projects;

    DeleteProjectUseCase deleteProjectUseCase;

    @BeforeEach
    void setUp() {
        deleteProjectUseCase = DeleteProjectUseCase.defaultService(projects);
    }

    @Test
    void deletion() {
        var projectId = randomUUID();

        deleteProjectUseCase.deleteProject(projectId);

        verify(projects).delete(projectId);
    }
}
