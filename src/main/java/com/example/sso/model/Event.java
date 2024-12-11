package com.example.sso.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    // Getter and setter for name
    @Getter
    @JsonProperty("name") // Ensures this field is included in JSON
    private String name;
    // Getter and setter for description
    @Getter
    private String description;
    private String location;
    private LocalDateTime dateTime;
    private LocalDateTime deadline;

    public Event(long l, String s, String s1, String s2, LocalDateTime now, LocalDateTime localDateTime) {
    }

    public Event() {

    }

    // Getter and setter for eventId (ID should match the field name)
    public Long getId() {
        return eventId;
    }

    public void setId(Long id) {
        this.eventId = id; // Fix the setter to use the parameter
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and setter for location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getter and setter for dateTime
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Getter and setter for deadline
    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
