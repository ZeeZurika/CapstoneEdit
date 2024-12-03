package org.zurika.zurikabookcapstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zurika.zurikabookcapstone.model.User;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);  // Find a user by username
    List<User> findByRole(String role);              // Find users by their role (Doctor, Patient, Admin)
    List<User> findAll();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

