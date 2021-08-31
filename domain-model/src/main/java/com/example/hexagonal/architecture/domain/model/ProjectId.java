package com.example.hexagonal.architecture.domain.model;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;

public final class ProjectId {

    private final UUID value;

    private ProjectId(UUID value) {
        this.value = requireNonNull(value, "The value must be null.");
    }

    public static ProjectId newIdentity() {
        return new ProjectId(randomUUID());
    }

    public static ProjectId valueOf(UUID uuid) {
        return new ProjectId(uuid);
    }

    public UUID toUUID() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectId that = (ProjectId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
