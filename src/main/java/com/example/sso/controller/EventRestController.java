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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/events")
public class EventRestController {

    private final EventService eventService;
    private final BookingService bookingService;

    @Autowired
    public EventRestController(EventService eventService, BookingService bookingService) {
        this.eventService = eventService;
        this.bookingService = bookingService;
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

}
