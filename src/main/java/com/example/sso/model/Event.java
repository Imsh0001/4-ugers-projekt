package com.example.sso.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String date;
    // Getters and setters for all fields including imageUrl
    @Setter
    @Getter
    private String imageUrl;  // URL til billedet

    // A set to hold the emails of members who signed up for the event
    @Setter
    @ElementCollection
    private Set<String> members = new HashSet<>();

    public Event(String name, String description, String date) {
    }

    public Event() {

    }

    // Method to add a member to the event
    public void addMember(String email) {
        this.members.add(email);
    }



    // ... rest of the model
}
