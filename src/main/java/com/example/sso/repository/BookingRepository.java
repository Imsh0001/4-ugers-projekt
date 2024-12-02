package com.example.sso.repository;

import com.example.sso.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByMember_memberIdAndEvent_eventId(Long memberId, Long eventId);
    List<Booking> findByEvent_eventId(Long eventId);
}
