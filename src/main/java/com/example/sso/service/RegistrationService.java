package com.example.sso.service;

import com.example.sso.model.Department;
import com.example.sso.model.MembershipType;
import com.example.sso.model.Registration;
import com.example.sso.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public Registration register(String name, String lastName, String email, String password, MembershipType membershipType, Department department) {

        if (name == null || name.isEmpty() ||
                lastName == null || lastName.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty() ||
                membershipType == null ||
                department == null) {
            throw new IllegalArgumentException("All fields, including department and membership type, must be provided");
        }


        Optional<Registration> existingUser = registrationRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }


        String registrationId = UUID.randomUUID().toString();


        Registration registration = new Registration();
        registration.setRegistrationId(registrationId);
        registration.setName(name);
        registration.setLastName(lastName);
        registration.setEmail(email);
        registration.setPassword(password);
        registration.setMembershipType(membershipType);
        registration.setDepartment(department);


        return registrationRepository.save(registration);
    }
}
