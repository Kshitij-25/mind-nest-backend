package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.dto.ApiResponse;
import com.kshitijcodecraft.mind_nest.dto.UserProfileRequest;
import com.kshitijcodecraft.mind_nest.service.UserProfileService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// UserProfileController.java
@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserProfileRequest>> createProfile(
            @Valid @RequestBody UserProfileRequest profileDTO) {
        try {
            UserProfileRequest createdProfile = userProfileService.createProfile(profileDTO);
            return ResponseEntity.ok(
                    ApiResponse.<UserProfileRequest>builder()
                            .status(HttpStatus.OK.value())
                            .data(createdProfile)
                            .message("Profile created successfully")
                            .build()
            );
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}