package org.zurika.zurikabookcapstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zurika.zurikabookcapstone.AppointmentNotFoundException;
import org.zurika.zurikabookcapstone.model.Appointment;
import org.zurika.zurikabookcapstone.model.User;
import org.zurika.zurikabookcapstone.service.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AppointmentService appointmentService;
    private final UserService userService;

    @Autowired
    public AdminController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    // Create a new user (Admin only)
    @GetMapping("/users/create")
    public String showCreateUserForm(Model model) {
        return "create-user";  // View for creating new user
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute User user, Model model) {
        try {
            userService.createUser(user);  // Create user
            return "redirect:/admin/users/list";  // Redirect to list all users after creation
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());  // Show error message if username/email exists
            return "create-user";
        }
    }

    // Delete a user (Admin only)
    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);  // Delete user by ID
        return "redirect:/admin/users/list";  // Redirect to list of all users
    }

    @GetMapping("/appointments/view/{appointmentId}")
    public String viewAppointmentDetails(@PathVariable Long appointmentId, Model model) {
        // Fetch the appointment by its ID, including the associated report
        Appointment appointment = appointmentService.getAppointmentById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        model.addAttribute("appointment", appointment);

        // Return the view that corresponds to the admin's report page
        return "admin-report";  // Corresponds to the 'admin-report.html' view
    }

    // 2. List all appointments for a specific doctor
    @GetMapping("/appointments/doctor/{doctorId}")
    public String listAppointmentsForDoctor(@PathVariable Long doctorId, Model model) {
        // Fetch the doctor using userService and then get appointments
        User doctor = userService.getUserById(doctorId);
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(doctor);

        model.addAttribute("appointments", appointments);
        model.addAttribute("doctor", doctor);

        return "list-doctor-appointments";  // View showing list of appointments for doctor
    }

    // 3. Cancel an appointment
    @GetMapping("/appointments/cancel/{appointmentId}")
    public String cancelAppointment(@PathVariable Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return "redirect:/admin/appointments/list";  // Redirect to the appointment list page
    }

    // 4. List all appointments (admin view)
    @GetMapping("/appointments/list")
    public String listAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(null);  // Get all appointments (or modify as needed)
        model.addAttribute("appointments", appointments);
        return "list-all-appointments";  // View showing all appointments
    }

    // 5. Admin can view all users (optional functionality for admin)
    @GetMapping("/users/list")
    public String listAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "list-all-users";  // View to display all users (patients, doctors, etc.)
    }

    // 6. Admin can view a specific user profile (optional functionality)
    @GetMapping("/users/view/{userId}")
    public String viewUserProfile(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "admin-user-profile";  // View for displaying the user's profile details
    }
}

