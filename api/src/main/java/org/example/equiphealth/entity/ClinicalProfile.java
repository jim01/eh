package org.example.equiphealth.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.*;
import lombok.Data;
import org.example.equiphealth.components.Clinician;
import org.example.equiphealth.components.UpdateListener;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Table(name = "clinical_profiles")
@Audited
@Data
@EntityListeners(UpdateListener.class)
public class ClinicalProfile implements Clinician {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "patient_id")
    private UUID patientId;

    @Column(name = "bmi")
    private Double bmi;

    @Column(name = "weight_loss")
    private Double weightLoss;

    @Column(name = "blood_pressure")
    private String bloodPressure;

    @Column(name = "heart_rate")
    private Integer heartRate;

    @Column(name = "clinical_indicators")
    private String clinicalIndicators;

    @Column(name = "ed_diagnosis")
    private String edDiagnosis;

    @Column(name = "ed_score")
    private Integer edScore;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "risk_score")
    private Double riskScore;

    @Column(name = "behaviors")
    private String behaviors;

    @Column(name = "clinician_id")
    private UUID clinicianId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setClinicianId(UUID clinicianId) {
        this.clinicianId = clinicianId;
    }


    @Override
    public String toString() {
        return "ClinicalProfile{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", bmi=" + bmi +
                ", weightLoss=" + weightLoss +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", heartRate=" + heartRate +
                ", clinicalIndicators='" + clinicalIndicators + '\'' +
                ", edDiagnosis='" + edDiagnosis + '\'' +
                ", edScore=" + edScore +
                ", riskLevel='" + riskLevel + '\'' +
                ", riskScore=" + riskScore +
                ", behaviors='" + behaviors + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
