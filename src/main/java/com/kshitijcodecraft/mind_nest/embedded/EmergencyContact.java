package com.kshitijcodecraft.mind_nest.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContact {
    private String emergencyName;
    private String emergencyRelationship;
    private String emergencyPhone;
}