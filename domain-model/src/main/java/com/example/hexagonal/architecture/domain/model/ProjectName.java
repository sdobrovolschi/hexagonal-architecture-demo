package com.example.hexagonal.architecture.domain.model;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class ProjectName {

    private final String value;

    private ProjectName(String value) {
        this.value = requireNonNull(value, "The value must not be null.");
    }

    public static ProjectName valueOf(String value) {
        return new ProjectName(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectName that = (ProjectName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
