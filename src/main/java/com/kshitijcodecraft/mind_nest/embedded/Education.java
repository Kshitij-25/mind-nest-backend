package com.kshitijcodecraft.mind_nest.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Embeddable
@Data
public class Education {
    private String degree;
    private String institution;
    private Integer graduationYear;

    @Column(length = 1000)
    private String specializations; // Store as comma-separated string

    // Helper method to get as list
    public List<String> getSpecializationsList() {
        return Arrays.asList(specializations.split(","));
    }

    // Helper method to set from list
    public void setSpecializationsList(List<String> specializations) {
        this.specializations = String.join(",", specializations);
    }
}