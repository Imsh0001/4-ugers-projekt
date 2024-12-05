package com.example.sso.controller;

import com.example.sso.model.Booking;
import com.example.sso.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Booking> createBooking(@RequestParam Long memberId, @RequestParam Long eventId) {
        try {
            Booking booking = bookingService.bookEvent(memberId, eventId);
            return ResponseEntity.ok(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Booking>> getBookingsForEvent(@PathVariable Long eventId) {
        List<Booking> bookings = bookingService.getBookingsForEvent(eventId);
        return ResponseEntity.ok(bookings);
    }

}
