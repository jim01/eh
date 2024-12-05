package org.example.equiphealth.repository;

import java.util.List;
import java.util.UUID;

import org.example.equiphealth.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, UUID> {
    List<Goal> findByClinicalProfileId(UUID clinicalProfileId);
} 