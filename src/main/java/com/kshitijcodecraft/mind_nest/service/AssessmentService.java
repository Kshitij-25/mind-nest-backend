package com.kshitijcodecraft.mind_nest.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kshitijcodecraft.mind_nest.dto.AssessmentRequest;
import com.kshitijcodecraft.mind_nest.entity.Assessment;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import com.kshitijcodecraft.mind_nest.repository.AssessmentRepository;

import lombok.RequiredArgsConstructor;

// AssessmentService.java
@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;

    public Assessment createAssessment(User user, AssessmentRequest request) {
        Assessment assessment = new Assessment();
        assessment.setUser(user);
        assessment.setTimestamp(LocalDateTime.now());

        // Calculate scores
        assessment.setPhq9Score(calculatePHQ9Score(request.getPhq9Responses()));
        assessment.setGad7Score(calculateGAD7Score(request.getGad7Responses()));
        assessment.setFunctionalImpactScore(mapFunctionalImpact(request.getFunctionalImpact()));

        // Set risk factors
        assessment.setSelfHarmThoughts(request.isSelfHarmThoughts());
        assessment.setSelfHarmPlan(request.isSelfHarmPlan());
        assessment.setSuicideHistory(request.isSuicideHistory());
        assessment.setUnsafeEnvironment(request.isUnsafeEnvironment());

        // Set recommendations
        String riskLevel = determineRiskLevel(assessment);
        assessment.setRiskLevel(riskLevel);
        assessment.setRecommendations(generateRecommendations(riskLevel));

        return assessmentRepository.save(assessment);
    }

    private int calculatePHQ9Score(List<Integer> responses) {
        return responses.stream().mapToInt(Integer::intValue).sum();
    }

    private int calculateGAD7Score(List<Integer> responses) {
        return responses.stream().mapToInt(Integer::intValue).sum();
    }

    private String determineRiskLevel(Assessment assessment) {
        // Crisis check first
        if (assessment.isSelfHarmPlan())
            return "CRISIS";

        // Then high risk factors
        if (assessment.isSelfHarmThoughts() ||
                assessment.isUnsafeEnvironment() ||
                assessment.getPhq9Score() >= 20 ||
                assessment.getGad7Score() >= 15) {
            return "HIGH";
        }

        // Moderate risk
        if (assessment.getPhq9Score() >= 10 ||
                assessment.getGad7Score() >= 10 ||
                assessment.getFunctionalImpactScore() >= 2) {
            return "MODERATE";
        }

        // Low risk
        return "LOW";
    }

    private int mapFunctionalImpact(String impact) {
        return switch (impact.toUpperCase()) {
            case "SEVERELY" -> 3;
            case "MODERATELY" -> 2;
            case "A LITTLE" -> 1;
            default -> 0; // "NOT AT ALL"
        };
    }

    private String generateRecommendations(String riskLevel) {
        return switch (riskLevel) {
            case "CRISIS" -> "EMERGENCY PROTOCOL: Please contact emergency services immediately. " +
                    "National Suicide Prevention Lifeline: 1-800-273-TALK (8255)";
            case "HIGH" -> "Urgent Care Needed: We recommend immediate consultation with a psychiatrist " +
                    "and daily check-ins. Available crisis resources: [LINK]";
            case "MODERATE" -> "Professional Support Recommended: Schedule weekly sessions with a " +
                    "licensed therapist from our network";
            default -> "Preventive Care: Try our self-guided resources and consider monthly " +
                    "check-ins with a counselor";
        };
    }

    public List<Assessment> getAssessmentsByUser(User user) {
        List<Assessment> assessments = assessmentRepository.findByUser(user);

        if (assessments.isEmpty()) {
            throw new CustomException("No assessments found for the user");
        }

        return assessments;
    }
}