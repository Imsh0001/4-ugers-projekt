package com.example.sso.service;

import com.example.sso.exception.ResourceNotFoundException;
import com.example.sso.model.Event;
import com.example.sso.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private FileStorageService fileStorageService;
    public Event createEvent(String name, String description, String date, MultipartFile image) {

        String imageUrl = "/" + fileStorageService.storeFile(image);



        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setDate(date);
        event.setImageUrl(imageUrl);


        return eventRepository.save(event);
    }

    private String saveImageToFileSystem(MultipartFile image) {
        try {
            String filePath = "/uploads/" + image.getOriginalFilename();
            File dest = new File(filePath);
            image.transferTo(dest);
            return filePath;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save image");
        }
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        event.setName(updatedEvent.getName());
        event.setDescription(updatedEvent.getDescription());
        event.setDate(updatedEvent.getDate());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        eventRepository.delete(event);
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void addMemberToEvent(Long eventId, String memberEmail) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
        event.addMember(memberEmail);
        eventRepository.save(event);
    }

    public List<Event> getEventsForMember(String memberEmail) {

        return eventRepository.findByMembersContaining(memberEmail);


    }
}