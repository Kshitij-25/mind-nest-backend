package com.kshitijcodecraft.mind_nest.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class AssessmentRequest {
    private String email;
    // PHQ-9 Questions (0-3 scale)
    private List<Integer> phq9Responses;

    // GAD-7 Questions (0-3 scale)
    private List<Integer> gad7Responses;

    // Risk Assessment
    private boolean selfHarmThoughts;
    private boolean selfHarmPlan;
    private boolean suicideHistory;
    private boolean unsafeEnvironment;

    // Functional Impact
    private String functionalImpact; // NOT_AT_ALL, LITTLE, MODERATE, SEVERE

    // Treatment History
    private boolean currentTreatment;
    private String diagnosisHistory;
    private String currentMedication;

    // Goals
    private Set<String> therapyGoals;
}