package com.example.hexagonal.architecture.adapter.rest.config;

import com.fasterxml.jackson.annotation.JsonCreator;

interface MixIns {

    abstract class CreateProjectMixIn {

        @JsonCreator
        public CreateProjectMixIn(String name) {

        }
    }
}
