package com.example.sso;

import com.example.sso.model.Event;
import com.example.sso.repository.EventRepository;
import com.example.sso.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEvent_ShouldSaveEvent() {
        Event event = new Event();
        event.setName("Test Event");

        when(eventRepository.save(event)).thenReturn(event);

        Event savedEvent = eventService.createEvent(event);

        assertNotNull(savedEvent);
        assertEquals("Test Event", savedEvent.getName());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void getAllEvents_ShouldReturnEventList() {
        List<Event> events = Arrays.asList(new Event(), new Event());
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void getEventById_ShouldReturnEvent_WhenExists() {
        Event event = new Event();
        event.setId(1L);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.getEventById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void getEventById_ShouldReturnEmpty_WhenNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Event> result = eventService.getEventById(1L);

        assertFalse(result.isPresent());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void updateEvent_ShouldUpdateAndReturnEvent_WhenExists() {
        Event existingEvent = new Event();
        existingEvent.setId(1L);
        existingEvent.setName("Old Name");

        Event updatedEvent = new Event();
        updatedEvent.setName("New Name");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(existingEvent)).thenReturn(existingEvent);

        Event result = eventService.updateEvent(1L, updatedEvent);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(existingEvent);
    }

    @Test
    void updateEvent_ShouldThrowException_WhenNotFound() {
        Event updatedEvent = new Event();
        updatedEvent.setName("New Name");

        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            eventService.updateEvent(1L, updatedEvent);
        });

        assertEquals("Event not found with ID: 1", exception.getMessage());
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void deleteEvent_ShouldDeleteEvent_WhenExists() {
        when(eventRepository.existsById(1L)).thenReturn(true);

        eventService.deleteEvent(1L);

        verify(eventRepository, times(1)).existsById(1L);
        verify(eventRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEvent_ShouldThrowException_WhenNotFound() {
        when(eventRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            eventService.deleteEvent(1L);
        });

        assertEquals("Event not found with ID: 1", exception.getMessage());
        verify(eventRepository, times(1)).existsById(1L);
        verify(eventRepository, never()).deleteById(1L);
    }
}
