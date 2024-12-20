package com.example.sso.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
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
    private MembershipType membershipType;

    @Enumerated(EnumType.STRING)
    private Department department;


    public User() {
    }


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