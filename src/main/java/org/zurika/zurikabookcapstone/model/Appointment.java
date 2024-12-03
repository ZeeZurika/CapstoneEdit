package org.zurika.zurikabookcapstone.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Column(nullable = false)
    private LocalDateTime dateTime;  // Add validation if necessary

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)  // Use Enum for status to ensure consistent values
    private AppointmentStatus status;

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = true)  // If the report is optional
    private Report report;


    // Enum for Appointment Status
    public enum AppointmentStatus {
        SCHEDULED,
        COMPLETED,
        CANCELLED
    }
}
