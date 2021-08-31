package com.example.hexagonal.architecture.adapter.persistence;

import com.example.hexagonal.architecture.domain.model.Project;
import com.example.hexagonal.architecture.domain.model.ProjectId;
import com.example.hexagonal.architecture.domain.model.ProjectName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.UUID;

@Entity
@Table(name = "project")
@NoArgsConstructor // for JPA
@Getter
final class ProjectRecord implements Persistable<UUID> {

    @Id
    private UUID id;
    private String name;

    @Transient
    private boolean isNew;

    public ProjectRecord(UUID id, String name) {
        this.id = id;
        this.name = name;

        isNew = true;
    }

    static ProjectRecord valueOf(Project project) {
        return new ProjectRecord(
                project.getProjectId().toUUID(),
                project.getName().toString()
        );
    }

    public Project toProject() {
        return new Project(
                ProjectId.valueOf(id),
                ProjectName.valueOf(name)
        );
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
