package com.example.sso.controller;

import com.example.sso.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.sso.model.Event;
import com.example.sso.service.EventService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService;

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(@RequestParam("name") String name,
                                             @RequestParam("description") String description,
                                             @RequestParam("date") String date,
                                             @RequestParam("image") MultipartFile image) {
        String imageUrl = fileStorageService.storeFile(image); // Store the image and get the URL

        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setDate(date);
        event.setImageUrl(imageUrl);

        Event createdEvent = eventService.createEvent(name, description, date, image);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
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

