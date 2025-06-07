package com.kshitijcodecraft.mind_nest.dto.professionals;

import lombok.Data;

@Data
public class AvailabilityDTO {
    private DailyAvailabilityDTO dailyAvailability;  // E.g., { "monday": "9AM-5PM" }
    private String timeZone;
    private Boolean acceptingNewClients;
}

