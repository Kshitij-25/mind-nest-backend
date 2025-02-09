package com.kshitijcodecraft.mind_nest.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kshitijcodecraft.mind_nest.embedded.EmergencyContact;
import com.kshitijcodecraft.mind_nest.embedded.TherapyPreferences;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    @JsonBackReference  // ✅ Prevent infinite recursion
    private User user;

    // Personal Information
    private String fullName;
    private String profilePicUrl;
    private LocalDate dateOfBirth;
    private String genderIdentity;
    private String preferredPronouns;
    private String email;
    private String phoneNumber;
    private String location;

    // Emergency Contact
    @Embedded
    private EmergencyContact emergencyContact;

    // Preferences
    @Embedded
    private TherapyPreferences therapyPreferences;
}

