package org.example.equiphealth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.equiphealth.components.Clinician;
import org.example.equiphealth.components.UpdateListener;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assessments")
@Audited
@Data
@EntityListeners(UpdateListener.class)
public class Assessment implements Clinician {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "intervention_id", nullable = false)
    private UUID interventionId;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "suicidal_ideation_score")
    private Double suicidalIdeationScore;

    @Column(name = "medical_risk_notes")
    private String medicalRiskNotes;

    @Column(name = "progress_score")
    private Double progressScore;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "clinician_id")
    private UUID clinicianId;

    public void setClinicianId(UUID clinicianId) {
        this.clinicianId = clinicianId;
    }

}
