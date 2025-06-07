package com.kshitijcodecraft.mind_nest.dto.professionals;

import lombok.Data;

@Data
public class CredentialsDTO {
    private String professionalTitle;
    private String licenseNumber;
    private String issuingAuthority;
    private String licenseExpiry;  // Changed to String (optional date handling)
    private Boolean multipleLicenses;
    private Boolean boardCertified;
}