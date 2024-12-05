package org.example.equiphealth.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalResponse {
    private UUID id;
    private String name;
    private String status;
    private String type;
    @Builder.Default
    private List<InterventionResponse> interventions = new ArrayList<>();
} 