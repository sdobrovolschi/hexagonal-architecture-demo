package com.example.hexagonal.architecture.domain.model;

import static java.util.UUID.fromString;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class TestProjectBuilder {

    private ProjectId projectId = ProjectId.newIdentity();
    private ProjectName projectName = ProjectName.valueOf(randomAlphanumeric(5));

    public static TestProjectBuilder randomProject() {
        return new TestProjectBuilder();
    }

    public TestProjectBuilder withProjectId(String projectId) {
        this.projectId = ProjectId.valueOf(fromString(projectId));
        return this;
    }

    public TestProjectBuilder withName(String name) {
        this.projectName = ProjectName.valueOf(name);
        return this;
    }

    public Project build() {
        return new Project(projectId, projectName);
    }
}
