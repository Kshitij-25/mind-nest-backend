package com.kshitijcodecraft.mind_nest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kshitijcodecraft.mind_nest.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.kshitijcodecraft.mind_nest.dto.ApiResponse;
import com.kshitijcodecraft.mind_nest.dto.ProfileRequest;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.entity.UserProfile;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import com.kshitijcodecraft.mind_nest.service.ProfileService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

//    @PostMapping(path = "/create-user-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ApiResponse<UserProfile>> createOrUpdateProfile(
//            @RequestPart(value = "profileData") String profileDataString,
//            @RequestPart(value = "profilePic", required = false) MultipartFile profilePic
//    ) {
//        try {
//            // Configure ObjectMapper for proper date handling
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.registerModule(new JavaTimeModule());
//            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
//
//            // Add debug logging
//            log.debug("Received profileData: {}", profileDataString);
//
//            // Parse the JSON string to ProfileRequest object
//            ProfileRequest request = mapper.readValue(profileDataString, ProfileRequest.class);
//
//            // Fetch user by email from request
//            String userEmail = request.getEmail();
//            User user = userRepository.findByEmail(userEmail)
//                    .orElseThrow(() -> new CustomException("User not found"));
//
//            // Handle profile picture upload if provided
//            if (profilePic != null && !profilePic.isEmpty()) {
//                String imageUrl = fileStorageService.uploadFile(profilePic);
//                request.setProfilePicUrl(imageUrl);
//            }
//
//            UserProfile profile = profileService.saveProfile(user, request);
//            return ResponseEntity.ok(new ApiResponse<>("Profile saved successfully", HttpStatus.OK.value(), null));
//        } catch (JsonProcessingException e) {
//            log.error("JSON parsing error: ", e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ApiResponse<>("Invalid JSON format: " + e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
//        } catch (CustomException e) {
//            log.error("Custom error: ", e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
//        } catch (Exception e) {
//            log.error("Unexpected error: ", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
//        }
//    }

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