package com.kshitijcodecraft.mind_nest.controller;

import com.kshitijcodecraft.mind_nest.dto.AppointmentRequest;
import com.kshitijcodecraft.mind_nest.entity.Appointment;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody AppointmentRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(appointmentService.createAppointment(request, user));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getUserAppointments(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByUser(user));
    }
}