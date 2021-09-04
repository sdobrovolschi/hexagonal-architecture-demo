package com.example.hexagonal.architecture;

import java.util.UUID;

public final class ProjectInfo {

    private final UUID projectId;
    private final String name;

    public ProjectInfo(UUID projectId, String name) {
        this.projectId = projectId;
        this.name = name;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }
}
