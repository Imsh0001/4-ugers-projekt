package com.example.sso.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)  // Ensures that username is unique in the database
    private String username;

    @Column(unique = true)  // Ensures that email is unique in the database
    private String email;

    private String password;
    private String role;

    private String education;
    @Enumerated(EnumType.STRING)
    private StudyField studyField;

    private String educationLevel;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType; // ACTIVE or PASSIVE

    @Enumerated(EnumType.STRING)
    private Department department; // Optional; only relevant if membershipType is ACTIVE

    // Default constructor (necessary for JPA)
    public User() {}

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

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public StudyField getStudyField() {
        return studyField;
    }

    public void setStudyField(StudyField studyField) {
        this.studyField = studyField;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

}
