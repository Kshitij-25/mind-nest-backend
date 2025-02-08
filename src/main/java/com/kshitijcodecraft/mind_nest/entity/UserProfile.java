package com.kshitijcodecraft.mind_nest.entity;

import com.kshitijcodecraft.mind_nest.dto.UserProfileRequest;
import com.kshitijcodecraft.mind_nest.enums.Enums;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Enums.GenderIdentity genderIdentity;

    private String preferredPronouns;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String country;

    // Emergency Contact
    private String emergencyContactName;
    private String emergencyContactRelationship;
    private String emergencyContactPhone;

    // Therapy Preferences
    @NotNull
    private String preferredLanguage;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Enums.TherapyMode preferredMode;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Enums.ProfessionalType preferredProfessionalType;

    private String preferredTherapistGender;

    @NotNull
    private boolean consentGiven;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Convert to DTO
    public UserProfileRequest toDTO() {
        return UserProfileRequest.builder()
                .id(this.id)
                .fullName(this.fullName)
                .dateOfBirth(this.dateOfBirth)
                .genderIdentity(this.genderIdentity)
                .preferredPronouns(this.preferredPronouns)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .city(this.city)
                .state(this.state)
                .country(this.country)
                .emergencyContactName(this.emergencyContactName)
                .emergencyContactRelationship(this.emergencyContactRelationship)
                .emergencyContactPhone(this.emergencyContactPhone)
                .preferredLanguage(this.preferredLanguage)
                .preferredMode(this.preferredMode)
                .preferredProfessionalType(this.preferredProfessionalType)
                .preferredTherapistGender(this.preferredTherapistGender)
                .consentGiven(this.consentGiven)
                .build();
    }
}

