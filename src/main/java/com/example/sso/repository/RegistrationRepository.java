package com.example.sso.repository;

import com.example.sso.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    Optional<Registration> findByEmail(String email);
}
