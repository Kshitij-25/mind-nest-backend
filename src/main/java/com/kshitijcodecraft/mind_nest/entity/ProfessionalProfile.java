package com.kshitijcodecraft.mind_nest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kshitijcodecraft.mind_nest.embedded.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Professional.java
@Entity
@Data
@Table(name = "professional_profile")
public class ProfessionalProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    @JsonBackReference  // ✅ Prevent infinite recursion
    private User user;

    // Personal Information
    private String fullName;
    private LocalDate dateOfBirth;
    @Column(length = 2048)
    private String profilePicUrl;
    private String genderIdentity;
    private String preferredPronouns;
    private String contactNumber;
    private String email;
    private String address;

    // Professional Credentials
    @Embedded
    private ProfessionalCredentials credentials;

    // Education
    @ElementCollection
    @CollectionTable(
            name = "professional_education",
            joinColumns = @JoinColumn(name = "professional_id")
    )
    private List<Education> educationHistory;

    // Experience
    @Embedded
    private ProfessionalExperience experience;

    // Therapy Approach
    @ElementCollection
    private Set<String> therapeuticModalities;

    // Availability
    @Embedded
    private AvailabilitySchedule availability;

    // Insurance & Payment
    @Embedded
    private PaymentInformation paymentInfo;

    // Legal
    private boolean backgroundCheckConsent;
    private boolean termsAccepted;

    private String specialty;
    private Double rating;

    // Initialize documents map to prevent NPE
    @ElementCollection
    private Map<String, String> documents = new HashMap<>();
}