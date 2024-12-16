package com.example.sso.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
public class Event {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String date;

    // A set to hold the emails of members who signed up for the event
    @ElementCollection
    private Set<String> members = new HashSet<>();

    // Default constructor (necessary for JPA)
    public Event() {}

    // Constructor with parameters
    public Event(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    // Method to add a member to the event
    public void addMember(String email) {
        this.members.add(email);
    }

}
