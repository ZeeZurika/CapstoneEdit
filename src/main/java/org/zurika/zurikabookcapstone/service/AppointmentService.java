package org.zurika.zurikabookcapstone.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zurika.zurikabookcapstone.AppointmentNotFoundException;
import org.zurika.zurikabookcapstone.model.*;
import org.zurika.zurikabookcapstone.repository.AppointmentRepository;
import org.zurika.zurikabookcapstone.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    // Create a new appointment
    @Transactional
    public void createAppointment(Appointment appointment) {
        // Save the appointment to the database
        appointmentRepository.save(appointment);
    }

    // Check if there is a conflicting appointment for the same patient or doctor at the same time
    public boolean hasConflictingAppointment(Appointment appointment) {
        boolean patientConflict = appointmentRepository.existsByPatientAndDateTime(appointment.getPatient(), appointment.getDateTime());
        boolean doctorConflict = appointmentRepository.existsByDoctorAndDateTime(appointment.getDoctor(), appointment.getDateTime());
        return patientConflict || doctorConflict;
    }

    // Method to get an appointment by ID
    public Optional<Appointment> getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    // Get all appointments for a specific patient
    public List<Appointment> getAppointmentsForPatient(User patient) {
        return appointmentRepository.findByPatient(patient);
    }

    // Get all appointments for a specific doctor
    public List<Appointment> getAppointmentsForDoctor(User doctor) {
        return appointmentRepository.findByDoctor(doctor);
    }

    // Get appointments within a specific date range
    public List<Appointment> getAppointmentsBetween(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDateTimeBetween(start, end);
    }

    // Optional: Get an appointment by doctor and date
    public Optional<Appointment> getAppointmentForDoctorAndDate(User doctor, LocalDateTime dateTime) {
        return appointmentRepository.findByDoctorAndDateTime(doctor, dateTime);
    }

    @Transactional
    public void cancelAppointment(Long appointmentId) {
        // Fetch the appointment by its ID
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + appointmentId));

        // Set the appointment status to "CANCELLED"
        appointment.setStatus(Appointment.AppointmentStatus.valueOf("CANCELLED"));

        // Save the updated appointment back to the database
        appointmentRepository.save(appointment);
    }
}

