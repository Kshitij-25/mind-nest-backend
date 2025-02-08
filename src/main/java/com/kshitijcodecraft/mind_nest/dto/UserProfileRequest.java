package com.kshitijcodecraft.mind_nest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshitijcodecraft.mind_nest.entity.UserProfile;
import com.kshitijcodecraft.mind_nest.enums.Enums;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

// UserProfileDTO.java
@Data
@Builder
public class UserProfileRequest {
    private Long id;
    private String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Enums.GenderIdentity genderIdentity;
    private String preferredPronouns;
    private String email;
    private String phoneNumber;
    private String city;
    private String state;
    private String country;

    // Emergency Contact
    private String emergencyContactName;
    private String emergencyContactRelationship;
    private String emergencyContactPhone;

    // Therapy Preferences
    private String preferredLanguage;
    private Enums.TherapyMode preferredMode;
    private Enums.ProfessionalType preferredProfessionalType;
    private String preferredTherapistGender;
    private boolean consentGiven;

    // Convert to Entity
    public UserProfile toEntity() {
        UserProfile profile = new UserProfile();
        profile.setFullName(this.fullName);
        profile.setDateOfBirth(this.dateOfBirth);
        profile.setGenderIdentity(this.genderIdentity);
        profile.setPreferredPronouns(this.preferredPronouns);
        profile.setEmail(this.email);
        profile.setPhoneNumber(this.phoneNumber);
        profile.setCity(this.city);
        profile.setState(this.state);
        profile.setCountry(this.country);
        profile.setEmergencyContactName(this.emergencyContactName);
        profile.setEmergencyContactRelationship(this.emergencyContactRelationship);
        profile.setEmergencyContactPhone(this.emergencyContactPhone);
        profile.setPreferredLanguage(this.preferredLanguage);
        profile.setPreferredMode(this.preferredMode);
        profile.setPreferredProfessionalType(this.preferredProfessionalType);
        profile.setPreferredTherapistGender(this.preferredTherapistGender);
        profile.setConsentGiven(this.consentGiven);
        return profile;
    }
}