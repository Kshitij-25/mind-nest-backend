package com.kshitijcodecraft.mind_nest.dto.professionals;

import lombok.Data;

import java.util.List;

@Data
public class ExperienceDTO {
    private Integer yearsExperience;
    private List<String> expertiseAreas;
    private List<String> preferredAgeGroups;
    private List<String> languagesSpoken;
    private Boolean offersCouplesTherapy;
    private Boolean offersGroupTherapy;
}
