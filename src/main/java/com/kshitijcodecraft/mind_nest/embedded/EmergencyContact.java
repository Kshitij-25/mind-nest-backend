package com.kshitijcodecraft.mind_nest.embedded;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EmergencyContact {
    private String emergencyName;
    private String emergencyRelationship;
    private String emergencyPhone;
}