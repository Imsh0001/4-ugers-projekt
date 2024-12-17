package com.example.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import com.example.sso.model.Event;
import com.example.sso.service.EventService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService;

    private static final String UPLOAD_DIR = "C:\\Users\\Azra\\Pictures\\images";


    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(@RequestParam("name") String name,
                                             @RequestParam("description") String description,
                                             @RequestParam("date") String date,
                                             @RequestParam("image") MultipartFile image) throws IOException {

        // Check if the image is empty
        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        // Save the image and get the image URL
        String imageUrl = saveImage(image);

        // Create the event
        Event event = new Event(name, description, date);
        event.setImageUrl(imageUrl);
        Event createdEvent = eventService.createEvent(event);

        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    // Helper method to save image and return the file path or URL
    private String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
        Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return "Users\\Azra\\Pictures\\images" + fileName;  // URL to access the image
    }

    @PutMapping("/updateEvent/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Event event = eventService.updateEvent(id, updatedEvent);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getEvent/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        Event event = eventService.getEvent(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}

