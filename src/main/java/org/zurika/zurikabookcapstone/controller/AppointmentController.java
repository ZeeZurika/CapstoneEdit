package org.zurika.zurikabookcapstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zurika.zurikabookcapstone.model.*;
import org.zurika.zurikabookcapstone.service.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    public AppointmentController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    // 1. Show appointment form for the patient (Create Appointment)
    @GetMapping("/create")
    public String showCreateAppointmentForm(Model model) {
        // Get the currently logged-in user (Patient)
        User currentPatient = userService.getCurrentUser();

        // Initialize new appointment object and set the current user as the patient
        Appointment appointment = new Appointment();
        appointment.setPatient(currentPatient);

        // Fetch a list of doctors to show in the form
        List<User> doctors = userService.getUsersByRole("DOCTOR");

        // Add the appointment and doctor list to the model
        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctors);

        return "create-appointment";  // This corresponds to create-appointment.html
    }

    // 2. Handle the appointment creation after form submission (POST)
    @PostMapping("/create")
    public String createAppointment(@ModelAttribute Appointment appointment, Model model) {
        // Check if the doctor or the patient already has an appointment at the given time
        if (appointmentService.hasConflictingAppointment(appointment)) {
            // If conflict exists, return the form with an error message
            model.addAttribute("error", "This appointment time is already taken by either the doctor or the patient.");
            return "create-appointment";  // Return to the same form with error
        }

        // Save the appointment if no conflict
        appointmentService.createAppointment(appointment);

        // Redirect to the patient's appointment list page
        return "redirect:/appointments/list";  // Redirect to patient-appointments.html
    }

    // 3. View all appointments for the current patient
    @GetMapping("/list")
    public String listPatientAppointments(Model model) {
        // Get the current user (patient)
        User currentPatient = userService.getCurrentUser();

        // Fetch appointments for the patient
        List<Appointment> appointments = appointmentService.getAppointmentsForPatient(currentPatient);

        // Add appointments to the model for rendering in the view
        model.addAttribute("appointments", appointments);

        return "patient-appointments"; // This corresponds to patient-appointments.html
    }

    // 4. View all appointments for a specific doctor (Admin view)
    @GetMapping("/doctor/{doctorId}")
    public String listDoctorAppointments(@PathVariable Long doctorId, Model model) {
        // Fetch the doctor user by id
        User doctor = userService.getUserById(doctorId);

        // Fetch appointments for the doctor
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(doctor);

        // Add appointments and doctor info to the model
        model.addAttribute("appointments", appointments);
        model.addAttribute("doctor", doctor);

        return "list-doctor-appointments"; // This corresponds to list-doctor-appointments.html
    }

    // 5. Show appointment details (For the patient or doctor to view specific details)
    @GetMapping("/details/{appointmentId}")
    public String viewAppointmentDetails(@PathVariable Long appointmentId, Model model) {
        // Fetch the appointment details
        Appointment appointment = appointmentService.getAppointmentById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        model.addAttribute("appointment", appointment);

        return "view-appointment"; // This corresponds to view-appointment.html
    }

    // 6. Admin or Doctor: Cancel an appointment
    @GetMapping("/cancel/{appointmentId}")
    public String cancelAppointment(@PathVariable Long appointmentId, Model model) {
        // Fetch the appointment by ID
        Appointment appointment = appointmentService.getAppointmentById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Cancel the appointment (for example, set its status to 'CANCELLED')
        appointment.setStatus(Appointment.AppointmentStatus.valueOf("CANCELLED"));
        appointmentService.createAppointment(appointment);  // Save the updated appointment

        // Redirect to the list of appointments (either for patient or doctor)
        return "redirect:/appointments/list";  // Redirects to patient-appointments.html
    }
}



