package com.example.sso;

import com.example.sso.model.Booking;
import com.example.sso.model.Event;
import com.example.sso.model.Member;
import com.example.sso.repository.BookingRepository;
import com.example.sso.service.BookingService;
import com.example.sso.service.EventService;
import com.example.sso.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private BookingService bookingService;

    private Member member;
    private Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize Member and Event objects
        member = new Member();
        member.setMemberId(1L);
        member.setName("John Doe");

        event = new Event();
        event.setId(1L);
        event.setName("Music Concert");
    }

    @Test
    public void testBookEvent_Success() {
        // Mocking the repository and service methods
        when(bookingRepository.existsByMember_memberIdAndEvent_eventId(1L, 1L)).thenReturn(false); // No booking exists
        when(memberService.getMemberById(1L)).thenReturn(member); // Member exists
        when(eventService.getEventById(1L)).thenReturn(Optional.of(event)); // Event exists

        // Create a booking that would be returned by the service
        Booking booking = new Booking();
        booking.setMember(member);
        booking.setEvent(event);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking); // Mock save method

        // Call the method to test
        Booking createdBooking = bookingService.bookEvent(1L, 1L);

        // Assert the booking was created and saved
        assertNotNull(createdBooking, "Booking should not be null");
        assertEquals(member, createdBooking.getMember(), "Member in booking should match");
        assertEquals(event, createdBooking.getEvent(), "Event in booking should match");

        // Verify that the repository save method was called
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testBookEvent_Failure_AlreadyBooked() {
        // Mocking the repository method to simulate an existing booking
        when(bookingRepository.existsByMember_memberIdAndEvent_eventId(1L, 1L)).thenReturn(true);

        // Call the method and expect an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.bookEvent(1L, 1L);
        });

        // Assert the exception message
        assertEquals("Member already booked for this event.", exception.getMessage());

        // Verify the repository method was called
        verify(bookingRepository, times(1)).existsByMember_memberIdAndEvent_eventId(1L, 1L);
    }

    @Test
    public void testBookEvent_Failure_EventNotFound() {
        // Mocking the repository method to simulate no event found
        when(bookingRepository.existsByMember_memberIdAndEvent_eventId(1L, 1L)).thenReturn(false); // No booking exists
        when(memberService.getMemberById(1L)).thenReturn(member); // Member exists
        when(eventService.getEventById(1L)).thenReturn(Optional.empty()); // Event not found

        // Call the method and expect an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.bookEvent(1L, 1L);
        });

        // Assert the exception message
        assertEquals("Event not found", exception.getMessage());

        // Verify that the event service method was called
        verify(eventService, times(1)).getEventById(1L);
    }
}
