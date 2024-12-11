package com.example.sso.controller;

import com.example.sso.model.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("CORS test successful!");
    }
    @GetMapping
    public List<Event> getEvents() {
        // Example data for testing
        return List.of(
                new Event(1L, "Event 1", "Description 1", "", LocalDateTime.now(), LocalDateTime.now().plusDays(5)),
                new Event(2L, "Event 2", "Description 2", "Location 2", LocalDateTime.now(), LocalDateTime.now().plusDays(10))
        );
    }

}
