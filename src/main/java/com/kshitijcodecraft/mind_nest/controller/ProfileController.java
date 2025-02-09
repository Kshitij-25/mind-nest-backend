package com.kshitijcodecraft.mind_nest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kshitijcodecraft.mind_nest.dto.ApiResponse;
import com.kshitijcodecraft.mind_nest.dto.ProfileRequest;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.entity.UserProfile;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import com.kshitijcodecraft.mind_nest.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    @PostMapping("/create-user-profile")
    public ResponseEntity<ApiResponse<UserProfile>> createOrUpdateProfile(
            @RequestBody ProfileRequest request // Remove Principal parameter
    ) {
        try {
            // Remove Principal checks
            // Example: Fetch user by email from request (adjust as needed)
            String userEmail = request.getEmail(); // Add email to ProfileRequest
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new CustomException("User not found"));

            UserProfile profile = profileService.saveProfile(user, request);
            return ResponseEntity.ok(new ApiResponse<>("Profile saved successfully", HttpStatus.OK.value(), null));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    @GetMapping("/get-user-profile")
    public ResponseEntity<ApiResponse<UserProfile>> getMyProfile(@AuthenticationPrincipal User user) {
        try {
            UserProfile profile = profileService.getProfileByUser(user);
            return ResponseEntity.ok(
                    new ApiResponse<>("Profile retrieved successfully", HttpStatus.OK.value(), profile));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
        }
    }
}