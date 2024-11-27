package com.example.sso.repository;

import com.example.sso.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    boolean existsByMemberIdAndEventId(Long memberId, Long eventId);
}
