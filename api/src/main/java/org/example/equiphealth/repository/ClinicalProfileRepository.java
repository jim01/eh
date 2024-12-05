package org.example.equiphealth.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.example.equiphealth.entity.ClinicalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicalProfileRepository extends JpaRepository<ClinicalProfile, UUID> {
    List<ClinicalProfile> findByPatientId(UUID patientId);
} 