package com.kshitijcodecraft.mind_nest.embedded;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Embeddable
@Data
public class AvailabilitySchedule {
    @ElementCollection
    @CollectionTable(
            name = "professional_availability",
            joinColumns = @JoinColumn(name = "professional_id")
    )
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "time_range", length = 50)
    private Map<String, String> dailyAvailability = new HashMap<>();

    @Column(length = 50)
    private String timeZone;

    private boolean acceptingNewClients;
}