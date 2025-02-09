package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.dto.AssessmentRequest;
import com.kshitijcodecraft.mind_nest.entity.Assessment;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// AssessmentController.java
@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<Assessment> createAssessment(
            @RequestBody AssessmentRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(assessmentService.createAssessment(user, request));
    }

    @GetMapping
    public ResponseEntity<List<Assessment>> getAssessmentHistory(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(assessmentService.getAssessmentsByUser(user));
    }
}