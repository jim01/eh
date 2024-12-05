package org.example.equiphealth.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResponse {
    private UUID id;
    private String type;
    private String findings;
    private String recommendations;
    private LocalDateTime assessmentDate;
    private String assessor;
    private String status;
} 