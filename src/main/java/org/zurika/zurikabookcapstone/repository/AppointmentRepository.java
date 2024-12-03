package org.zurika.zurikabookcapstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zurika.zurikabookcapstone.model.Appointment;
import org.zurika.zurikabookcapstone.model.User;

import java.time.LocalDateTime;
import java.util.*;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(Long id);
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByDoctor(User doctor);
    List<Appointment> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    Optional<Appointment> deleteById(long id);

    Optional<Appointment> findByDoctorAndDateTime(User doctor, LocalDateTime dateTime);

    // Check if an appointment already exists for the doctor at the given time
    boolean existsByDoctorAndDateTime(User doctor, LocalDateTime dateTime);

    // Check if an appointment already exists for the patient at the given time
    boolean existsByPatientAndDateTime(User patient, LocalDateTime dateTime);
}
