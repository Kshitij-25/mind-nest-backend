package com.kshitijcodecraft.mind_nest.service;

import com.kshitijcodecraft.mind_nest.dto.AssessmentRequest;
import com.kshitijcodecraft.mind_nest.entity.Assessment;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.repository.AssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;

    public Assessment createAssessment(AssessmentRequest request, User user) {
        Assessment assessment = new Assessment();
        assessment.setScore(request.getScore());
        assessment.setAssessmentType(request.getAssessmentType());
        assessment.setResponses(request.getResponses()); // Store as JSON string
        assessment.setTimestamp(LocalDateTime.now());
        assessment.setUser(user);
        return assessmentRepository.save(assessment);
    }
}