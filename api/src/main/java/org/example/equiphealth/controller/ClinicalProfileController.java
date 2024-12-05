package org.example.equiphealth.controller;

import java.util.List;
import java.util.UUID;

import org.example.equiphealth.dto.ClinicalProfileRequest;
import org.example.equiphealth.dto.ClinicalProfileResponse;
import org.example.equiphealth.service.ClinicalProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clinical-profiles")
public class ClinicalProfileController {

    @Autowired
    private ClinicalProfileService clinicalProfileService;

    @PostMapping
    public ResponseEntity<ClinicalProfileResponse> createClinicalProfile(
            @RequestBody ClinicalProfileRequest request) {
        return ResponseEntity.ok(clinicalProfileService.createClinicalProfile(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicalProfileResponse> getClinicalProfile(
            @PathVariable UUID id) {
        System.out.println("Getting clinical profile with id: " + id);
        ClinicalProfileResponse response = clinicalProfileService.getClinicalProfile(id);
        System.out.println("Response: " + response.toString());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicalProfileResponse> updateClinicalProfile(
            @PathVariable UUID id,
            @RequestBody ClinicalProfileRequest request) {
        return ResponseEntity.ok(clinicalProfileService.updateClinicalProfile(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinicalProfile(@PathVariable UUID id) {
        clinicalProfileService.deleteClinicalProfile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ClinicalProfileResponse>> getClinicalProfilesByPatientId(
            @PathVariable UUID patientId) {
        return ResponseEntity.ok(clinicalProfileService.getClinicalProfilesByPatientId(patientId));
    }
} 
