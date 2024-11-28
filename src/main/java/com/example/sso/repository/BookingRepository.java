package com.example.sso.repository;

import com.example.sso.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByMember_memberIdAndEvent_eventId(Long memberId, Long eventId);
}
