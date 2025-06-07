package com.kshitijcodecraft.mind_nest.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapyPreferences {
    private String preferredLanguage;
    private String therapyMode; // video, phone, text, etc.
    private String professionalType;
    private String preferredProfessionalGender;
}