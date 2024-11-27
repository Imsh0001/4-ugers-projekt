package com.example.sso.controller;

import com.example.sso.model.Department;
import com.example.sso.model.MembershipType;
import com.example.sso.model.Registration;
import com.example.sso.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @PostMapping
    public ResponseEntity<Registration> register(@RequestBody Registration registration) {
        try {
            Registration createdRegistration = registrationService.register(registration.getName(),
                    registration.getLastName(),
                    registration.getEmail(),
                    registration.getPassword(),
                    registration.getMembershipType(),
                    registration.getDepartment());

            return new ResponseEntity<>(createdRegistration, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    }

