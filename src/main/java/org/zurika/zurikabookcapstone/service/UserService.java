package org.zurika.zurikabookcapstone.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zurika.zurikabookcapstone.model.User;
import org.zurika.zurikabookcapstone.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. Create a new user
    @Transactional
    public User createUser(User user) {
        // You can add validation or business logic here before saving the user
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        return userRepository.save(user);  // Save the user to the database
    }

    // 2. Delete a user by ID (Soft Delete Option)
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Soft delete (set the 'deleted' flag instead of deleting the record)
        // user.setDeleted(true);  // Assuming 'deleted' field exists in your User model
        // userRepository.save(user);

        // For now, we'll perform a hard delete:
        userRepository.deleteById(userId);  // Remove the user from the database
    }

    // Add a method to get User by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    // Retrieve the currently logged-in user from SecurityContext
    public User getCurrentUser() {
        // Assuming SecurityContext is used for authentication and user retrieval
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Fetch users by their role (e.g., Doctor, Patient, Admin)
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);  // Assuming you have a role-based query
    }

    // Get all users (for admin to view all users)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

