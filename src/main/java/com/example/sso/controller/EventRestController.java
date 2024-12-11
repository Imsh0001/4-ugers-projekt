package com.example.sso.controller;

import com.example.sso.model.Booking;
import com.example.sso.model.Event;
import com.example.sso.service.BookingService;
import com.example.sso.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/api/events")
public class EventRestController {


    private final EventService eventService;
    private final BookingService bookingService;

    @Autowired
    public EventRestController(EventService eventService, BookingService bookingService) {
        this.eventService = eventService;
        this.bookingService = bookingService;
    }
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents(); // Assuming you have this method in your service
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/registrations")
    public ResponseEntity<Object> getEventRegistrations(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        if (event.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Booking> bookings = bookingService.getBookingsForEvent(id);
        return ResponseEntity.ok(Map.of(
                "event", event.get(),
                "registrations", bookings,
                "totalRegistrations", bookings.size()
        ));
    }
    // Create a new event
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event newEvent = eventService.saveEvent(event);
        System.out.println("Created Event: " + newEvent);  // Debugging output
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }


    // Update an existing event
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        Optional<Event> existingEvent = eventService.getEventById(id);
        if (existingEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Event updatedEvent = eventService.updateEvent(id, eventDetails);
        return ResponseEntity.ok(updatedEvent);
    }

    // Delete an event
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully.");
    }
    // Member registration for an event
    @PostMapping("/{id}/register")
    public ResponseEntity<?> registerMemberForEvent(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        Long memberId = request.get("memberId");
        if (memberId == null) {
            return ResponseEntity.badRequest().body("Member ID is required");
        }

        try {
            Booking booking = bookingService.bookEvent(memberId, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
