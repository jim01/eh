package org.example.equiphealth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PatientResponse {
    private UUID id;
    
    private String firstName;
    
    private String lastName;
    
    private LocalDate dob;
    
    private String phone;
    
    private String email;
    
    private LocalDate createdAt;
    
    private LocalDate updatedAt;
} 
