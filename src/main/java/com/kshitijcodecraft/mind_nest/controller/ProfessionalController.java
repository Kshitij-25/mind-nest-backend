package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.entity.Appointment;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professional")
@RequiredArgsConstructor
public class ProfessionalController {

    private final AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getProfessionalAppointments(
            @AuthenticationPrincipal User professional
    ) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByProfessional(professional));
    }

    @PostMapping("/appointments/{appointmentId}/complete")
    public ResponseEntity<Appointment> completeAppointment(
            @PathVariable Long appointmentId
    ) {
        return ResponseEntity.ok(appointmentService.completeAppointment(appointmentId));
    }
}