package com.example.hexagonal.architecture.application;

public final class CreateProject {

    private final String name;

    public CreateProject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
