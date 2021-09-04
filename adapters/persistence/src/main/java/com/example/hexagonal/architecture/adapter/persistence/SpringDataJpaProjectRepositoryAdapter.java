package com.example.hexagonal.architecture.adapter.persistence;

import com.example.hexagonal.architecture.domain.model.Project;
import com.example.hexagonal.architecture.domain.model.ProjectId;
import com.example.hexagonal.architecture.domain.model.Projects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

@Component
@Slf4j
@RequiredArgsConstructor
final class SpringDataJpaProjectRepositoryAdapter implements Projects {

    private final SpringDataJpaProjectRepository delegate;

    @Override
    public boolean add(Project project) {
        var record = ProjectRecord.valueOf(project);

        try {
            delegate.saveAndFlush(record);
        } catch (DataIntegrityViolationException e) {
            if (defaultIfEmpty(e.getMessage(), EMPTY).contains("uq_project_name")) {
                log.debug("The insertion of a duplicate project name failed: {}", project.getName(), e);
                return false;
            }
            throw e;
        }
        return true;
    }

    @Override
    public List<Project> findAll() {
        return delegate.findAll(Sort.by(Order.asc("name"))).stream()
                .map(ProjectRecord::toProject)
                .collect(toUnmodifiableList());
    }

    @Override
    public Optional<Project> find(ProjectId projectId) {
        return delegate.findById(projectId.toUUID())
                .map(ProjectRecord::toProject);
    }

    @Override
    public void delete(UUID projectId) {
        delegate.deleteById(projectId);
    }
}
