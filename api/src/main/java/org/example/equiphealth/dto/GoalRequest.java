package org.example.equiphealth.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalRequest {
    private String name;
    private String description;
    private String type;
    @Builder.Default
    private List<InterventionRequest> interventions = new ArrayList<>();
} 