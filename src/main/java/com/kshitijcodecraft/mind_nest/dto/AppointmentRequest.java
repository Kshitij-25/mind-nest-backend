package com.kshitijcodecraft.mind_nest.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long professionalId;
}