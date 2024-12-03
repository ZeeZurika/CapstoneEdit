package org.zurika.zurikabookcapstone.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 4, max = 20)  // You can adjust the min and max size based on your needs
    private String username;

    @Column(nullable = false, unique = true)
    @Email(message = "Please provide a valid email address.")
    private String email;

    @Column(nullable = false)
    @Length(min = 8)  // Ensure password is at least 8 characters long
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;  // Consider using an enum for roles (ADMIN, DOCTOR, PATIENT, etc.)

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phone;  // Change to String to accommodate phone number formats

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private int zipCode;

    // Enum for role representation (optional but recommended)
    public enum Role {
        ADMIN, DOCTOR, PATIENT
    }

    //private boolean isActive = true;

    //public boolean hasRole(String role) {
        //return this.role != null && this.role.equals(role);
    //}
}