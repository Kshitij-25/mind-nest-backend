package com.kshitijcodecraft.mind_nest.embedded;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Set;

@Embeddable
@Data
public class PaymentInformation {
    private boolean acceptsInsurance;
    @ElementCollection
    private Set<String> acceptedInsurances;
    private String sessionFees;
    private boolean slidingScaleAvailable;
}