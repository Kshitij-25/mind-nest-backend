package com.kshitijcodecraft.mind_nest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kshitijcodecraft.mind_nest.embedded.EmergencyContact;
import com.kshitijcodecraft.mind_nest.embedded.TherapyPreferences;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    private String fullName;
    private String profilePicUrl;
    private String email;
    private LocalDate dateOfBirth;
    private String genderIdentity;
    private String preferredPronouns;
    private String phoneNumber;
    private String location;

    private EmergencyContact emergencyContact;
    private TherapyPreferences therapyPreferences;

    private boolean dataConsent;
    private boolean emergencyDisclaimerAck;
}