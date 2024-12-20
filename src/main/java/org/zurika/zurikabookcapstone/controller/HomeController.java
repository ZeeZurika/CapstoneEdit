package org.zurika.zurikabookcapstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    // Method to display the home page
    @GetMapping
    public String showHomePage() {
        return "home"; // This is the page with the role selection form (home.html)
    }

    // Handle the form submission for role selection
    @PostMapping("/select-role")
    public String handleRoleSelection(@RequestParam("role") String role) {
        // Redirect to role-specific page based on the role selected
        return switch (role) {
            case "PATIENT" -> "redirect:/patient/dashboard"; // Redirect to patient-specific page
            case "DOCTOR" -> "redirect:/doctor/dashboard"; // Redirect to doctor-specific page
            case "ADMIN" -> "redirect:/admin/dashboard"; // Redirect to admin dashboard
            default -> "redirect:/home"; // Default redirect if no role is selected
        };
    }
}

