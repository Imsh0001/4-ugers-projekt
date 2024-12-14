package com.example.sso.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {

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

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    // Method to add a member to the event
    public void addMember(String email) {
        this.members.add(email);
    }

}
