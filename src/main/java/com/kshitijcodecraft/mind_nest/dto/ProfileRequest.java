package com.kshitijcodecraft.mind_nest.dto;

import com.kshitijcodecraft.mind_nest.embedded.EmergencyContact;
import com.kshitijcodecraft.mind_nest.embedded.TherapyPreferences;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileRequest {
    private String fullName;
    private String profilePicUrl;
    private String email;
    private LocalDate dateOfBirth;
    private String genderIdentity;
    private String preferredPronouns;
    private String phoneNumber;
    private String city;
    private String state;
    private String country;

    private EmergencyContact emergencyContact;
    private TherapyPreferences therapyPreferences;

    private boolean dataConsent;
    private boolean emergencyDisclaimerAck;
}