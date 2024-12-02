package com.example.sso;


import com.example.sso.model.Department;
import com.example.sso.model.MembershipType;
import com.example.sso.model.Registration;
import com.example.sso.repository.RegistrationRepository;
import com.example.sso.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldRegisterNewUser_WhenInputIsValid() {
        // Arrange
        String name = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        MembershipType membershipType = MembershipType.ACTIVE;
        Department department = Department.FUNDING;

        when(registrationRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(registrationRepository.save(any(Registration.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Registration result = registrationService.register(name, lastName, email, password, membershipType, department);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(lastName, result.getLastName());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        assertEquals(membershipType, result.getMembershipType());
        assertEquals(department, result.getDepartment());
        assertNotNull(result.getRegistrationId());
        verify(registrationRepository, times(1)).findByEmail(email);
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        String email = "john.doe@example.com";

        Registration existingUser = new Registration();
        existingUser.setEmail(email);

        when(registrationRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.register("John", "Doe", email, "password123", MembershipType.ACTIVE, Department.FUNDING);
        });

        assertEquals("Email is already registered", exception.getMessage());
        verify(registrationRepository, times(1)).findByEmail(email);
        verify(registrationRepository, never()).save(any(Registration.class));
    }

    @Test
    void register_ShouldThrowException_WhenAnyFieldIsNullOrEmpty() {
        // Arrange
        String name = "John";
        String lastName = "";
        String email = "john.doe@example.com";
        String password = "password123";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.register(name, lastName, email, password, MembershipType.ACTIVE, Department.FUNDING);
        });

        assertEquals("All fields, including department and membership type, must be provided", exception.getMessage());
        verify(registrationRepository, never()).findByEmail(anyString());
        verify(registrationRepository, never()).save(any(Registration.class));
    }
}
