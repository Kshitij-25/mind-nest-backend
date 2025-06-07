package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.dto.ApiResponse;
import com.kshitijcodecraft.mind_nest.dto.AssessmentRequest;
import com.kshitijcodecraft.mind_nest.entity.Assessment;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.entity.UserProfile;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import com.kshitijcodecraft.mind_nest.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// AssessmentController.java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final UserRepository userRepository;

    @PostMapping("/save-assessments")
    public ResponseEntity<ApiResponse<Assessment>> createAssessment(
            @RequestBody AssessmentRequest request
//            @AuthenticationPrincipal User user
    ) {
//        // Remove Principal checks
//        // Example: Fetch user by email from request (adjust as needed)
//        String userEmail = request.getEmail(); // Add email to ProfileRequest
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new CustomException("User not found"));
//        return ResponseEntity.ok(assessmentService.createAssessment(user, request));
        try {
            String userEmail = request.getEmail(); // Add email to ProfileRequest
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new CustomException("User not found"));
            Assessment assessment = assessmentService.createAssessment(user, request);
            return ResponseEntity.ok(new ApiResponse<Assessment>("Assessment saved successfully", HttpStatus.OK.value(), null));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping
    public ResponseEntity<List<Assessment>> getAssessmentHistory(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(assessmentService.getAssessmentsByUser(user));
    }
}