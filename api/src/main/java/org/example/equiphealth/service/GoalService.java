package org.example.equiphealth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.equiphealth.dto.GoalRequest;
import org.example.equiphealth.dto.GoalResponse;
import org.example.equiphealth.entity.Goal;
import org.example.equiphealth.entity.ClinicalProfile;
import org.example.equiphealth.repository.GoalRepository;
import org.example.equiphealth.repository.ClinicalProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private ClinicalProfileRepository clinicalProfileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public GoalResponse createGoal(UUID clinicalProfileId, GoalRequest request) {
        Goal goal = objectMapper.convertValue(request, Goal.class);
        
        ClinicalProfile clinicalProfile = clinicalProfileRepository.findById(clinicalProfileId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinical Profile not found"));
        
        goal.setClinicalProfileId(clinicalProfile.getId());
        Goal savedGoal = goalRepository.save(goal);
        
        return objectMapper.convertValue(savedGoal, GoalResponse.class);
    }

    public GoalResponse getGoal(UUID id) {
        Goal goal = goalRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found"));
        return objectMapper.convertValue(goal, GoalResponse.class);
    }

    public List<GoalResponse> getGoalsByClinicalProfileId(UUID clinicalProfileId) {
        List<Goal> goals = goalRepository.findByClinicalProfileId(clinicalProfileId);
        return goals.stream()
            .map(goal -> objectMapper.convertValue(goal, GoalResponse.class))
            .collect(Collectors.toList());
    }

    public GoalResponse updateGoal(UUID id, GoalRequest request) {
        goalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found"));

        Goal updatedGoal = objectMapper.convertValue(request, Goal.class);
        updatedGoal.setId(id);
        Goal savedGoal = goalRepository.save(updatedGoal);
        return objectMapper.convertValue(savedGoal, GoalResponse.class);
    }

    public void deleteGoal(UUID id) {
        if (!goalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found");
        }
        goalRepository.deleteById(id);
    }
} 