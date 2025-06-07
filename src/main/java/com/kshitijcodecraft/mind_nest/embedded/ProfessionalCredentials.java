package com.kshitijcodecraft.mind_nest.embedded;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class ProfessionalCredentials {
    private String professionalTitle;
    private String licenseNumber;
    private String issuingAuthority;
    private LocalDate licenseExpiry;
    private boolean multipleLicenses;
    private boolean boardCertified;
}