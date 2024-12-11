package com.example.sso.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically increments the admin_id column
    @Column(name = "admin_id") // Explicitly set the column name to match the database column
    private Long id;

    // Getters and setters for username
    @Setter
    @Getter
    private String username;
    // Getters and setters for password
    @Setter
    @Getter
    private String password;

}
