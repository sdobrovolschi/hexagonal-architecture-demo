package com.example.hexagonal.architecture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public final class ProjectInfo {

    private final UUID projectId;
    private final String name;

    @JsonCreator
    public ProjectInfo(@JsonProperty("projectId") UUID projectId, @JsonProperty("name") String name) {
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
