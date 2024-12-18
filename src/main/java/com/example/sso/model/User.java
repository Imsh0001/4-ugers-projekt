package com.example.sso.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
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

    @Getter
    private String education;
    @Getter
    @Enumerated(EnumType.STRING)
    private StudyField studyField;

    @Getter
    private String educationLevel;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType; // ACTIVE or PASSIVE

    @Enumerated(EnumType.STRING)
    private Department department; // Optional; only relevant if membershipType is ACTIVE

    // Default constructor (necessary for JPA)
    public User() {
    }

    // Constructor with parameters
    public User(String username, String email, String password, String role, MembershipType membershipType, Department department, String education, StudyField studyField, String educationLevel) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.membershipType = membershipType;
        this.department = department;
        this.education = education;
        this.studyField = studyField;
        this.educationLevel = educationLevel;
    }
}