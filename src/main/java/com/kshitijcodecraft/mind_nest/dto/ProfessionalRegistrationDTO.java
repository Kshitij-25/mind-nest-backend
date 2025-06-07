package com.kshitijcodecraft.mind_nest.dto;

import com.kshitijcodecraft.mind_nest.dto.professionals.*;
import lombok.Data;
import java.util.List;

@Data
public class ProfessionalRegistrationDTO {
    // Personal Info
    private Integer id;  // Matching Flutter's `id`
    private String fullName;
    private String profileImageUrl;  // New Field
    private String dateOfBirth;      // Changed to String for flexibility
    private String genderIdentity;
    private String preferredPronouns;
    private String contactNumber;
    private String email;
    private String address;

    // Credentials
    private CredentialsDTO credentials;  // Nested DTO for professional credentials

    // Education History
    private List<EducationDTO> educationHistory;  // List matching Flutter

    // Experience
    private ExperienceDTO experience;  // Nested DTO to capture years, expertise, etc.

    // Therapy Approach
    private List<String> therapeuticModalities;  // Changed from Set to List for consistency
    private String therapyDescription;

    // Availability
    private AvailabilityDTO availability;  // Nested DTO for better structure

    // Payment Info
    private PaymentInfoDTO paymentInfo;  // Matches Flutter's PaymentInfoModel

    // Legal
    private Boolean backgroundCheckConsent;  // Nullable to match Flutter's optionality
    private Boolean termsAccepted;
}
