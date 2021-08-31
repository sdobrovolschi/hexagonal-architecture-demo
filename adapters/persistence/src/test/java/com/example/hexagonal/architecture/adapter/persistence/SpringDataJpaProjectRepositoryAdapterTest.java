package com.example.hexagonal.architecture.adapter.persistence;

import com.example.hexagonal.architecture.domain.model.Project;
import com.example.hexagonal.architecture.domain.model.ProjectId;
import com.example.hexagonal.architecture.domain.model.ProjectName;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.hexagonal.architecture.domain.model.TestProjectBuilder.randomProject;
import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@PersistenceTest
class SpringDataJpaProjectRepositoryAdapterTest {

    @Autowired
    SpringDataJpaProjectRepositoryAdapter repository;

    @Autowired
    EntityManager entityManager;

    @ExpectedDataSet("datasets/project.yaml")
    @Test
    void insertion() {
        var project = new Project(
                ProjectId.valueOf(fromString("fe5cea5a-71ce-44cd-9b7a-1aba11dc5a91")),
                ProjectName.valueOf("Test Project")
        );

        repository.add(project);
    }

    @DataSet("datasets/project.yaml")
    @Test
    void idUniqueness() {
        var project = randomProject()
                .withProjectId("fe5cea5a-71ce-44cd-9b7a-1aba11dc5a91")
                .build();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> repository.add(project))
                .withMessageContaining("pk_project_id");

    }

    @DataSet("datasets/project.yaml")
    @Test
    void nameUniqueness() {
        var project = randomProject()
                .withName("Test Project")
                .build();

        var result = repository.add(project);

        assertThat(result)
                .as("check insertion result")
                .isFalse();
    }

    @DataSet("datasets/project.yaml")
    @ParameterizedTest
    @MethodSource("selectionArgumentsProvider")
    void selection(ProjectId projectId, Project expectedProject) {
        var project = repository.find(projectId);

        assertThat(project)
                .usingRecursiveComparison()
                .isEqualTo(Optional.ofNullable(expectedProject));
    }

    static Stream<Arguments> selectionArgumentsProvider() {
        return Stream.of(
                arguments(ProjectId.valueOf(fromString("fe5cea5a-71ce-44cd-9b7a-1aba11dc5a91")),
                        new Project(
                                ProjectId.valueOf(fromString("fe5cea5a-71ce-44cd-9b7a-1aba11dc5a91")),
                                ProjectName.valueOf("Test Project")
                        )),

                arguments(ProjectId.valueOf(randomUUID()), null)
        );
    }
}
