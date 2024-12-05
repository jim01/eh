package org.example.equiphealth.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.equiphealth.components.Clinician;
import org.example.equiphealth.components.UpdateListener;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "interventions")
@Audited
@Data
@EntityListeners(UpdateListener.class)
public class Intervention implements Clinician {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID interventionId;

    @Column(name = "goal_id", nullable = false)
    private UUID goalId;

    private String type;
    private String description;

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
