package com.kshitijcodecraft.mind_nest.dto.professionals;

import lombok.Data;

import java.util.List;

@Data
public class PaymentInfoDTO {
    private Boolean acceptsInsurance;
    private List<String> acceptedInsurances;
    private String sessionFees;
    private Boolean slidingScaleAvailable;
}
