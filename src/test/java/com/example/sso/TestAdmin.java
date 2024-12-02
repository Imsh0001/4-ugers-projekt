package com.example.sso;

import com.example.sso.model.Admin;
import com.example.sso.repository.AdminRepository;
import com.example.sso.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestAdmin {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        admin = new Admin();
        admin.setUsername("adminUser");
        admin.setPassword("adminPassword");
    }

    @Test
    public void testVerifyAdminCredentials_Success() {
        // Mocking repository behavior
        when(adminRepository.findByUsername("adminUser")).thenReturn(Optional.of(admin));

        // Test the method
        Optional<Admin> result = adminService.verifyAdminCredentials("adminUser", "adminPassword");

        assertTrue(result.isPresent());
        assertEquals(admin, result.get());

        // Verify that the repository method was called
        verify(adminRepository, times(1)).findByUsername("adminUser");
    }

    @Test
    public void testVerifyAdminCredentials_Failure_InvalidUsername() {
        // Mocking repository behavior for invalid username
        when(adminRepository.findByUsername("invalidUser")).thenReturn(Optional.empty());

        // Test the method
        Optional<Admin> result = adminService.verifyAdminCredentials("invalidUser", "adminPassword");

        assertFalse(result.isPresent());

        // Verify that the repository method was called
        verify(adminRepository, times(1)).findByUsername("invalidUser");
    }

    @Test
    public void testVerifyAdminCredentials_Failure_InvalidPassword() {
        // Mocking repository behavior for correct username but wrong password
        when(adminRepository.findByUsername("adminUser")).thenReturn(Optional.of(admin));

        // Test the method with wrong password
        Optional<Admin> result = adminService.verifyAdminCredentials("adminUser", "wrongPassword");

        assertFalse(result.isPresent());

        // Verify that the repository method was called
        verify(adminRepository, times(1)).findByUsername("adminUser");
    }
}
