package org.example.equiphealth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.equiphealth.dto.PatientRequest;
import org.example.equiphealth.dto.PatientResponse;
import org.example.equiphealth.entity.Patient;
import org.example.equiphealth.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = objectMapper.convertValue(request, Patient.class);
        Patient savedPatient = patientRepository.save(patient);
        return objectMapper.convertValue(savedPatient, PatientResponse.class);
    }

    @Transactional(readOnly = true)
    public PatientResponse getPatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "Patient not found with id: " + id));
        return objectMapper.convertValue(patient, PatientResponse.class);
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patient -> objectMapper.convertValue(patient, PatientResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public PatientResponse updatePatient(UUID id, PatientRequest request) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "Patient not found with id: " + id));

        // Convert request to Patient but preserve the ID
        Patient updatedPatient = objectMapper.convertValue(request, Patient.class);
        updatedPatient.setId(existingPatient.getId());
        
        Patient savedPatient = patientRepository.save(updatedPatient);
        return objectMapper.convertValue(savedPatient, PatientResponse.class);
    }

    @Transactional
    public void deletePatient(UUID id) {
        if (!patientRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
} 
