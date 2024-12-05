package org.example.equiphealth.repository;

import org.example.equiphealth.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    
    // Find patients by last name containing (case-insensitive)
    List<Patient> findByLastNameContainingIgnoreCase(String lastName);

    // Find patients by first name and last name (case-insensitive)
    List<Patient> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(
        String firstName, String lastName);
}
