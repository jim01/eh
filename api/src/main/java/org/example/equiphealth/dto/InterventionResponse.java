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
public class InterventionResponse {
    private UUID id;

    @Builder.Default
    private List<AssessmentResponse> assessments = new ArrayList<>();

    private String type;
    private String description;
    private String status;
    private LocalDateTime scheduledDate;
    private LocalDateTime completionDate;
    private String outcome;
} 