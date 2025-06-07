package com.kshitijcodecraft.mind_nest.embedded;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Set;

@Embeddable
@Data
public class ProfessionalExperience {
    private Integer yearsExperience;
    @ElementCollection
    private Set<String> expertiseAreas;
    @ElementCollection
    private Set<String> preferredAgeGroups;
    @ElementCollection
    private Set<String> languagesSpoken;
    private boolean offersCouplesTherapy;
    private boolean offersGroupTherapy;
}