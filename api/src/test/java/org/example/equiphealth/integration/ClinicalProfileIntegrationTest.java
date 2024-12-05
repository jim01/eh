package org.example.equiphealth.integration;

import org.example.equiphealth.dto.ClinicalProfileRequest;
import org.example.equiphealth.dto.GoalRequest;
import org.example.equiphealth.entity.ClinicalProfile;
import org.example.equiphealth.entity.Patient;
import org.example.equiphealth.repository.ClinicalProfileRepository;
import org.example.equiphealth.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClinicalProfileIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicalProfileRepository clinicalProfileRepository;

    private Patient testPatient;
    private UUID patientId;

    @BeforeEach
    void setUp() {
        clinicalProfileRepository.deleteAll();
        patientRepository.deleteAll();

        // Create test patient
        testPatient = new Patient();
        testPatient.setFirstName("Test");
        testPatient.setLastName("Patient");
        testPatient = patientRepository.save(testPatient);
        patientId = testPatient.getId();
    }

    @Test
    void shouldCreateClinicalProfile() throws Exception {
        ClinicalProfileRequest request = createTestRequest();

        mockMvc.perform(post("/clinical-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.patientId").value(patientId.toString()))
                .andExpect(jsonPath("$.goals[0].name").value("Test Goal"));
    }

    @Test
    void shouldFetchClinicalProfile() throws Exception {
        // Create a profile first
        ClinicalProfile profile = createTestProfile();
        ClinicalProfile savedProfile = clinicalProfileRepository.save(profile);

        mockMvc.perform(get("/clinical-profiles/{id}", savedProfile.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedProfile.getId().toString()));
    }

    private ClinicalProfileRequest createTestRequest() {
        GoalRequest goalRequest = GoalRequest.builder()
                .name("Test Goal")
                .description("Reach Test Goal")
                .type("WEIGHT_LOSS")
                .build();

        return ClinicalProfileRequest.builder()
                .patientId(patientId)
                .bmi(22.5)
                .weightLoss(2.0)
                .bloodPressure("120/80")
                .heartRate(72)
                .goals(Arrays.asList(goalRequest))
                .build();
    }

    private ClinicalProfile createTestProfile() {
        ClinicalProfile profile = new ClinicalProfile();
        profile.setPatientId(patientId);
        profile.setBmi(22.5);
        profile.setWeightLoss(2.0);
        profile.setBloodPressure("120/80");
        profile.setHeartRate(72);
        return profile;
    }
} 
