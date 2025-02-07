package com.kshitijcodecraft.mind_nest.service;

import com.kshitijcodecraft.mind_nest.dto.AppointmentRequest;
import com.kshitijcodecraft.mind_nest.entity.Appointment;
import com.kshitijcodecraft.mind_nest.entity.User;
import com.kshitijcodecraft.mind_nest.exception.CustomException;
import com.kshitijcodecraft.mind_nest.repository.AppointmentRepository;
import com.kshitijcodecraft.mind_nest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public Appointment createAppointment(AppointmentRequest request, User user) {
        User professional = userRepository.findById(request.getProfessionalId())
                .orElseThrow(() -> new CustomException("Professional not found"));

        if (!professional.getRole().equals("PROFESSIONAL")) {
            throw new CustomException("User is not a professional");
        }

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setProfessional(professional);
        appointment.setStartTime(request.getStartTime());
        appointment.setEndTime(request.getEndTime());
        appointment.setStatus("SCHEDULED");

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByUser(User user) {
        return appointmentRepository.findByUser(user);
    }

    public List<Appointment> getAppointmentsByProfessional(User professional) {
        return appointmentRepository.findByProfessional(professional);
    }

    public Appointment completeAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new CustomException("Appointment not found"));
        appointment.setStatus("COMPLETED");
        return appointmentRepository.save(appointment);
    }
}