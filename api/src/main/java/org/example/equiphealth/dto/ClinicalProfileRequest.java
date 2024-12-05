package org.example.equiphealth.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalProfileRequest {
    private UUID patientId;
    private Double bmi;
    private Double weightLoss;
    private String bloodPressure;
    private Integer heartRate;
    private String clinicalIndicators;
    private String edDiagnosis;
    private Integer edScore;
    private String riskLevel;
    private Double riskScore;
    private String behaviors;
    
    @Builder.Default
    private List<GoalRequest> goals = new ArrayList<>();
}
