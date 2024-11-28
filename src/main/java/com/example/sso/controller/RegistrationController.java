package com.example.sso.controller;

import com.example.sso.dto.RegistrationRequest;
import com.example.sso.model.Registration;
import com.example.sso.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        try {
            Registration registration = registrationService.register(
                    registrationRequest.getName(),
                    registrationRequest.getLastName(),
                    registrationRequest.getEmail(),
                    registrationRequest.getPassword(),
                    registrationRequest.getMembershipType(),
                    registrationRequest.getDepartment()
            );
            return ResponseEntity.ok(registration);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred. Please try again.");
        }
    }
}
