package com.kshitijcodecraft.mind_nest.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kshitijcodecraft.mind_nest.embedded.EmergencyContact;
import com.kshitijcodecraft.mind_nest.embedded.TherapyPreferences;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Column(length = 2048)
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

