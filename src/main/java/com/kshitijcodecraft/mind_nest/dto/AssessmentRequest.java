package com.kshitijcodecraft.mind_nest.dto;

import lombok.Data;

@Data
public class AssessmentRequest {
    private int score; // Total score from the assessment
    private String assessmentType; // e.g., "PHQ-9", "GAD-7"
    private String responses; // JSON string of user's answers (optional)
}