package org.example.equiphealth.components;

import jakarta.persistence.PreUpdate;
import java.util.UUID;

public class UpdateListener {

    @PreUpdate
    public void setClinicianIdBeforeUpdate(Object entity) {
        // TODO Example of getting current logged in userId to set the last update
        // Get the current ID from SecurityContext (assuming Spring Security)
        // String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID clinicianId = UUID.fromString("52fd0c21-f699-49f8-b2d7-01fd1c213ae7");

        // Set the userId column in the entity
        if (entity instanceof Clinician) {
            ((Clinician) entity).setClinicianId(clinicianId);
        }
    }
}

