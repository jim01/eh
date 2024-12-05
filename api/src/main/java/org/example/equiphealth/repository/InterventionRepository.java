package org.example.equiphealth.repository;

import java.util.List;
import java.util.UUID;

import org.example.equiphealth.entity.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, UUID> {
    List<Intervention> findByGoalIdIn(List<UUID> goalIds);
} 