package com.example.sso.service;

import com.example.sso.model.Booking;
import com.example.sso.model.Event;
import com.example.sso.model.Member;
import com.example.sso.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EventService eventService;

    public Booking bookEvent(Long memberId, Long eventId) {
        // Check if booking already exists
        if (bookingRepository.existsByMember_memberIdAndEvent_eventId(memberId, eventId)) {
            throw new IllegalArgumentException("Member already booked for this event.");
        }

        // Fetch Member and Event objects
        Member member = memberService.getMemberById(memberId);
        Event event = eventService.getEventById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        // Create and save booking
        Booking booking = new Booking();
        booking.setMember(member);
        booking.setEvent(event);
        return bookingRepository.save(booking);

    }
    public List<Booking> getBookingsForEvent(Long eventId) {
        return bookingRepository.findByEvent_eventId(eventId);
    }
}
