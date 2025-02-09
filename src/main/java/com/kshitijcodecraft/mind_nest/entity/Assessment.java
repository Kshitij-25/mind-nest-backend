package com.kshitijcodecraft.mind_nest.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

// Assessment.java
@Entity
@Data
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime timestamp;

    // Screening Scores
    private int phq9Score;
    private int gad7Score;
    private int functionalImpactScore;

    // Risk Assessment
    private boolean selfHarmThoughts;
    private boolean selfHarmPlan;
    private boolean suicideHistory;
    private boolean unsafeEnvironment;

    // Treatment History
    private boolean currentTreatment;
    private String diagnosisHistory;
    private String currentMedication;

    // Goals
    @ElementCollection
    private Set<String> therapyGoals;

    // System Fields
    private String riskLevel; // LOW, MODERATE, HIGH, CRISIS
    private String recommendations;
}