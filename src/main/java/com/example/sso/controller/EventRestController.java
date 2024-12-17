package com.example.sso.controller;

import com.example.sso.model.Event;
import com.example.sso.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/events")
public class EventRestController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String date,
            @RequestParam MultipartFile image) {

        // Call the service method to create the event
        Event createdEvent = eventService.createEvent(name, description, date, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(id, event);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        Event event = eventService.getEvent(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
    // Endpoint to add a member to an event
    @PostMapping("/addMemberToEvent/{eventId}")
    public ResponseEntity<String> addMemberToEvent(@PathVariable Long eventId, @RequestBody Map<String, String> payload) {
        String memberEmail = payload.get("email");
        eventService.addMemberToEvent(eventId, memberEmail);
        return ResponseEntity.ok("Member added successfully.");
    }

}

