package org.zurika.zurikabookcapstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zurika.zurikabookcapstone.model.*;
import org.zurika.zurikabookcapstone.service.AppointmentService;
import org.zurika.zurikabookcapstone.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final AppointmentService appointmentService;
    private final UserService userService;

    @Autowired
    public PatientController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String patientDashboard() {
        return "patient-dashboard";  // The patient dashboard view
    }

    @GetMapping("/appointments")
    public String listPatientAppointments(Model model) {
        User patient = userService.getCurrentUser();  // Get the current logged-in patient
        List<Appointment> appointments = appointmentService.getAppointmentsForPatient(patient);  // Get appointments for the patient
        model.addAttribute("appointments", appointments);
        return "patient-appointments";  // View to show appointments for the patient
    }
}

