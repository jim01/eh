package org.example.equiphealth.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterventionRequest {
    private String type;
    private String description;
    private String status;
    private LocalDateTime scheduledDate;
} 