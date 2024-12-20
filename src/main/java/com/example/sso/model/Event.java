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
    private String imageUrl;


    @ElementCollection
    @CollectionTable(name = "event_members", joinColumns = @JoinColumn(name = "event_id"))
    private Set<String> members = new HashSet<>();


    public Event() {}


    public Event(String name, String description, String date, String imageUrl) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl != null && !imageUrl.startsWith("/uploads/")
                ? "/uploads/" + imageUrl
                : imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



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


    public void addMember(String email) {
        this.members.add(email);
    }


}
