package org.example.equiphealth.service;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.example.equiphealth.dto.ClinicalProfileRequest;
import org.example.equiphealth.dto.ClinicalProfileResponse;
import org.example.equiphealth.dto.GoalRequest;
import org.example.equiphealth.dto.GoalResponse;
import org.example.equiphealth.dto.InterventionResponse;
import org.example.equiphealth.entity.ClinicalProfile;
import org.example.equiphealth.entity.Goal;
import org.example.equiphealth.entity.Intervention;
import org.example.equiphealth.entity.Patient;
import org.example.equiphealth.repository.ClinicalProfileRepository;
import org.example.equiphealth.repository.GoalRepository;
import org.example.equiphealth.repository.InterventionRepository;
import org.example.equiphealth.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class ClinicalProfileService {
    
    @Autowired
    private ClinicalProfileRepository clinicalProfileRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private InterventionRepository interventionRepository;

    @Transactional
    public ClinicalProfileResponse createClinicalProfile(ClinicalProfileRequest request) {
        ClinicalProfile profile = objectMapper.convertValue(request, ClinicalProfile.class);
 
        // make sure patient exists
        patientRepository.findById(request.getPatientId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));


        ClinicalProfile savedProfile = clinicalProfileRepository.save(profile);

        ClinicalProfileResponse response = objectMapper.convertValue(savedProfile, ClinicalProfileResponse.class);

        for (GoalRequest goalRequest : request.getGoals()) {
            Goal goal = objectMapper.convertValue(goalRequest, Goal.class);
            goal.setClinicalProfileId(savedProfile.getId());
            goalRepository.save(goal);
            GoalResponse goalResponse = objectMapper.convertValue(goal, GoalResponse.class);
            response.getGoals().add(goalResponse);

            // save interventions for each goal
            List<Intervention> interventions = objectMapper.convertValue(goalRequest.getInterventions(), 
                objectMapper.getTypeFactory().constructCollectionType(List.class, Intervention.class));
            interventions.forEach(intervention -> intervention.setGoalId(goal.getId()));
            interventionRepository.saveAll(interventions);
            goalResponse.setInterventions(interventions.stream()
                .map(intervention -> objectMapper.convertValue(intervention, InterventionResponse.class))
                .collect(Collectors.toList()));
        }
        
        return response;
    }

    public ClinicalProfileResponse getClinicalProfile(UUID id) {
        ClinicalProfile profile = clinicalProfileRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinical Profile not found"));
        ClinicalProfileResponse response = objectMapper.convertValue(profile, ClinicalProfileResponse.class);
        List<Goal> goals = goalRepository.findByClinicalProfileId(id);

        response.setGoals(goals.stream()
            .map(goal -> objectMapper.convertValue(goal, GoalResponse.class))
            .collect(Collectors.toList()));

        List<Intervention> interventions = interventionRepository.findByGoalIdIn(
            goals.stream()
                .map(Goal::getId)
                .collect(Collectors.toList()));

        // group interventions by goal id so we can add them to the response under goal
        Map<UUID, List<InterventionResponse>> interventionsByGoalId = interventions.stream()
            .collect(Collectors.groupingBy(
                Intervention::getGoalId,
                Collectors.mapping(
                    intervention -> objectMapper.convertValue(intervention, InterventionResponse.class),
                    Collectors.toList()
                )
            ));

        response.getGoals().forEach(goalResponse -> goalResponse.setInterventions(
            interventionsByGoalId.getOrDefault(goalResponse.getId(), Collections.emptyList())));

        return response;
    }

    public ClinicalProfileResponse updateClinicalProfile(UUID id, ClinicalProfileRequest request) {
        // TODO: Implement
        return null;
    }

    public void deleteClinicalProfile(UUID id) {
        // TODO: Implement
    }

    public List<ClinicalProfileResponse> getClinicalProfilesByPatientId(UUID patientId) {
        List<ClinicalProfile> profiles = clinicalProfileRepository.findByPatientId(patientId);
        return profiles.stream()
            .map(profile -> objectMapper.convertValue(profile, ClinicalProfileResponse.class))
            .collect(Collectors.toList());
    }
} 