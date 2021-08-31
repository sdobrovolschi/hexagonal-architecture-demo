package com.example.hexagonal.architecture.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface SpringDataJpaProjectRepository extends JpaRepository<ProjectRecord, UUID> {
}
