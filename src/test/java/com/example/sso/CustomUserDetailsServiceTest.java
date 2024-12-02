package com.example.sso;

import com.example.sso.model.Admin;
import com.example.sso.model.Member;
import com.example.sso.repository.AdminRepository;
import com.example.sso.repository.MemberRepository;
import com.example.sso.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_ShouldReturnAdminDetails_WhenAdminExists() {
        // Arrange
        Admin admin = new Admin();
        admin.setUsername("admin_user");
        admin.setPassword("admin_pass");

        when(adminRepository.findByUsername("admin_user")).thenReturn(Optional.of(admin));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin_user");

        // Assert
        assertNotNull(userDetails);
        assertEquals("admin_user", userDetails.getUsername());
        assertEquals("admin_pass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        verify(adminRepository, times(1)).findByUsername("admin_user");
        verify(memberRepository, never()).findByEmail(anyString());
    }

    @Test
    void loadUserByUsername_ShouldReturnMemberDetails_WhenMemberExists() {
        // Arrange
        Member member = new Member();
        member.setEmail("member_user");
        member.setPassword("member_pass");

        when(adminRepository.findByUsername("member_user")).thenReturn(Optional.empty());
        when(memberRepository.findByEmail("member_user")).thenReturn(Optional.of(member));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("member_user");

        // Assert
        assertNotNull(userDetails);
        assertEquals("member_user", userDetails.getUsername());
        assertEquals("member_pass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_MEMBER")));
        verify(adminRepository, times(1)).findByUsername("member_user");
        verify(memberRepository, times(1)).findByEmail("member_user");
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(adminRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());
        when(memberRepository.findByEmail("unknown_user")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknown_user");
        });

        assertEquals("User not found", exception.getMessage());
        verify(adminRepository, times(1)).findByUsername("unknown_user");
        verify(memberRepository, times(1)).findByEmail("unknown_user");
    }
}
