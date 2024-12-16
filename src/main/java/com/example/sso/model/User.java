package com.example.sso.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class User {
    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)  // Ensures that username is unique in the database
    private String username;

    @Column(unique = true)  // Ensures that email is unique in the database
    private String email;

    private String password;
    private String role;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType; // ACTIVE or PASSIVE

    @Enumerated(EnumType.STRING)
    private Department department; // Optional; only relevant if membershipType is ACTIVE

    // Default constructor (necessary for JPA)
    public User() {}

    // Constructor with parameters
    public User(String username, String email, String password, String role, MembershipType membershipType, Department department) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.membershipType = membershipType;
        this.department = department;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
