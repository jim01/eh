package org.example.equiphealth.repository;

import java.util.UUID;

import org.example.equiphealth.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {
    // Define custom query methods if needed
} 