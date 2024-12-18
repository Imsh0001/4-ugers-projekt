
import com.example.sso.exception.ResourceNotFoundException;
import com.example.sso.model.Event;
import com.example.sso.repository.EventRepository;
import com.example.sso.service.EventService;
import com.example.sso.service.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private EventService eventService;

    @Mock
    private MultipartFile image;

    private Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setDescription("Test Description");
        event.setDate("2024-12-31");
        event.setImageUrl("/uploads/test_image.jpg");
    }

    @Test
    public void testCreateEvent() {
        // Arrange
        String name = "Test Event";
        String description = "Test Description";
        String date = "2024-12-31";
        String imagePath = "/uploads/test_image.jpg";

        when(fileStorageService.storeFile(image)).thenReturn(imagePath);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Act
        Event createdEvent = eventService.createEvent(name, description, date, image);

        // Assert
        assertNotNull(createdEvent);
        assertEquals("Test Event", createdEvent.getName());
        assertEquals("/uploads/test_image.jpg", createdEvent.getImageUrl());
    }

    @Test
    public void testUpdateEvent() {
        // Arrange
        Event updatedEvent = new Event();
        updatedEvent.setName("Updated Event");
        updatedEvent.setDescription("Updated Description");
        updatedEvent.setDate("2025-01-01");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        // Act
        Event result = eventService.updateEvent(1L, updatedEvent);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Event", result.getName());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    public void testDeleteEvent() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        doNothing().when(eventRepository).delete(event);

        // Act
        eventService.deleteEvent(1L);

        // Assert
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    public void testGetEvent() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        // Act
        Event result = eventService.getEvent(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetEventNotFound() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> eventService.getEvent(1L));
    }

    @Test
    public void testGetAllEvents() {
        // Arrange
        when(eventRepository.findAll()).thenReturn(List.of(event));

        // Act
        List<Event> events = eventService.getAllEvents();

        // Assert
        assertNotNull(events);
        assertEquals(1, events.size());
    }

    @Test
    public void testAddMemberToEvent() {
        // Arrange
        String memberEmail = "member@example.com";
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Act
        eventService.addMemberToEvent(1L, memberEmail);

        // Assert
        assertTrue(event.getMembers().contains(memberEmail));
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testGetEventsForMember() {
        // Arrange
        String memberEmail = "member@example.com";
        when(eventRepository.findByMembersContaining(memberEmail)).thenReturn(List.of(event));

        // Act
        List<Event> events = eventService.getEventsForMember(memberEmail);

        // Assert
        assertNotNull(events);
        assertEquals(1, events.size());
    }
}
