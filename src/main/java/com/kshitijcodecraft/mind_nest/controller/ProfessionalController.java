package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.dto.ApiResponse;
import com.kshitijcodecraft.mind_nest.dto.professionals.AvailabilityDTO;
import com.kshitijcodecraft.mind_nest.dto.professionals.DailyAvailabilityDTO;
import com.kshitijcodecraft.mind_nest.dto.professionals.DocumentUploadDTO;
import com.kshitijcodecraft.mind_nest.dto.ProfessionalRegistrationDTO;
import com.kshitijcodecraft.mind_nest.embedded.AvailabilitySchedule;
import com.kshitijcodecraft.mind_nest.entity.Appointment;
import com.kshitijcodecraft.mind_nest.entity.ProfessionalProfile;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import com.kshitijcodecraft.mind_nest.service.AppointmentService;
import com.kshitijcodecraft.mind_nest.service.FileStorageService;
import com.kshitijcodecraft.mind_nest.service.ProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfessionalController {

    private final AppointmentService appointmentService;
    private final ProfessionalService professionalService;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;

    // ✅ Create Professional Profile without Authentication
    @PostMapping("/create-professional-profile")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProfessionalProfile> createProfessionalProfile(
            @RequestBody ProfessionalRegistrationDTO registrationDTO
    ) {
        User user = userRepository.findByEmail(registrationDTO.getEmail())
                .orElseThrow(() -> new CustomException("User not found with email: " + registrationDTO.getEmail()));

        ProfessionalProfile professionalProfile = professionalService.createProfessional(user, registrationDTO);
        return new ApiResponse<>("Professional profile created successfully",
                HttpStatus.OK.value(),
                null);
    }

    // Upload document
    @PostMapping("/professional/{id}/documents")
    public ApiResponse<String> uploadDocument(
            @PathVariable Long id,
            @ModelAttribute DocumentUploadDTO uploadDTO,
            @AuthenticationPrincipal User user
    ) {
        String documentUrl = fileStorageService.uploadFile(uploadDTO.getFile());
        professionalService.addDocument(id, uploadDTO.getDocumentType(), documentUrl);
        return new ApiResponse<>("Document uploaded successfully", HttpStatus.OK.value(), documentUrl);
    }

    @GetMapping("/professionals")
    public ApiResponse<List<ProfessionalProfile>> getAllProfessionals() {
        return new ApiResponse<>("All professionals retrieved",
                HttpStatus.OK.value(),
                professionalService.getAllProfessionals());
    }

    @GetMapping("/professionals/recommended")
    public ApiResponse<List<ProfessionalProfile>> getRecommendedProfessionals(
            @RequestParam String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found"));

        return new ApiResponse<>("Recommended professionals retrieved",
                HttpStatus.OK.value(),
                professionalService.getRecommendedProfessionals(user));
    }

    @GetMapping("/professionals/filter")
    public ApiResponse<List<ProfessionalProfile>> filterProfessionals(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) String availability) {

        return new ApiResponse<>("Filtered professionals",
                HttpStatus.OK.value(),
                professionalService.filterProfessionals(specialty, location, minRating, availability));
    }

    // Get professional profile
    @GetMapping("/professional/{id}")
    public ApiResponse<ProfessionalProfile> getProfessional(@PathVariable Long id) {
        return new ApiResponse<>("Professional profile retrieved",
                HttpStatus.OK.value(),
                professionalService.getProfessionalById(id));
    }

    // Update availability
    @PutMapping("/{id}/availability")
    public ApiResponse<ProfessionalProfile> updateAvailability(
            @PathVariable Long id,
            @RequestBody AvailabilityDTO availabilityDTO
    ) {
        Map<String, String> availabilityMap = convertAvailabilityToMap(availabilityDTO.getDailyAvailability());
        return new ApiResponse<>("Availability updated",
                HttpStatus.OK.value(),
                professionalService.updateAvailability(id, availabilityMap));
    }

    private Map<String, String> convertAvailabilityToMap(DailyAvailabilityDTO dto) {
        Map<String, String> map = new HashMap<>();
        if (dto.getMonday() != null) map.put("monday", dto.getMonday());
        if (dto.getTuesday() != null) map.put("tuesday", dto.getTuesday());
        if (dto.getWednesday() != null) map.put("wednesday", dto.getWednesday());
        if (dto.getThursday() != null) map.put("thursday", dto.getThursday());
        if (dto.getFriday() != null) map.put("friday", dto.getFriday());
        if (dto.getSaturday() != null) map.put("saturday", dto.getSaturday());
        if (dto.getSunday() != null) map.put("sunday", dto.getSunday());
        return map;
    }

    @GetMapping("/professional/appointments")
    public ResponseEntity<List<Appointment>> getProfessionalAppointments(
            @AuthenticationPrincipal User professional
    ) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByProfessional(professional));
    }

    @PostMapping("/professional/appointments/{appointmentId}/complete")
    public ResponseEntity<Appointment> completeAppointment(
            @PathVariable Long appointmentId
    ) {
        return ResponseEntity.ok(appointmentService.completeAppointment(appointmentId));
    }
}