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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "goals")
@Audited
@Data
@EntityListeners(UpdateListener.class)
public class Goal implements Clinician {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "clinical_profile_id", nullable = false)
    private UUID clinicalProfileId;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;

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
