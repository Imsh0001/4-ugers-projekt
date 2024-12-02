package com.example.sso;

import com.example.sso.model.SSOinfo;
import com.example.sso.repository.SSOinfoRepository;
import com.example.sso.service.SSOinfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class SSOinfoServiceTest {

    @Mock
    private SSOinfoRepository ssoinfoRepository;

    @InjectMocks
    private SSOinfoService ssoinfoService;

    private SSOinfo ssoinfo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize SSOinfo object
        ssoinfo = new SSOinfo();
        ssoinfo.setContent("Initial Content");
    }

    @Test
    public void testGetSSOinfo_ReturnsSSOinfo() {
        // Mock the repository method to return an SSOinfo object
        when(ssoinfoRepository.findAll()).thenReturn(java.util.Collections.singletonList(ssoinfo));

        // Call the method to test
        SSOinfo result = ssoinfoService.getSSOinfo();

        // Assert the result is the same as the mock
        assertNotNull(result, "SSOinfo should not be null");
        assertEquals("Initial Content", result.getContent(), "Content should match the mock data");
    }

    @Test
    public void testGetSSOinfo_ReturnsNull_WhenNoSSOinfoExists() {
        // Mock the repository method to return an empty list
        when(ssoinfoRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        // Call the method to test
        SSOinfo result = ssoinfoService.getSSOinfo();

        // Assert the result is null
        assertNull(result, "SSOinfo should be null when no data exists");
    }

    @Test
    public void testUpdateSSOinfo_UpdatesContent() {
        // Mock the repository method to return the initial SSOinfo object
        when(ssoinfoRepository.findAll()).thenReturn(java.util.Collections.singletonList(ssoinfo));
        when(ssoinfoRepository.save(any(SSOinfo.class))).thenReturn(ssoinfo);

        // Call the method to test
        SSOinfo updatedSSOinfo = ssoinfoService.updateSSOinfo("Updated Content");

        // Assert the content was updated
        assertNotNull(updatedSSOinfo, "Updated SSOinfo should not be null");
        assertEquals("Updated Content", updatedSSOinfo.getContent(), "Content should be updated");
    }

    @Test
    public void testUpdateSSOinfo_CreatesNewSSOinfo_WhenNoneExists() {
        // Mock the repository method to return an empty list (no data exists)
        when(ssoinfoRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        // Mock the save method to return the newly created SSOinfo object
        when(ssoinfoRepository.save(any(SSOinfo.class))).thenAnswer(invocation -> {
            SSOinfo newSsoinfo = invocation.getArgument(0);
            newSsoinfo.setInfoId(1L); // Simulating the assignment of a generated ID
            return newSsoinfo;
        });

        // Call the method to test
        SSOinfo updatedSSOinfo = ssoinfoService.updateSSOinfo("Updated Content");

        // Assert the content was set correctly
        assertNotNull(updatedSSOinfo, "Updated SSOinfo should not be null");
        assertEquals("Updated Content", updatedSSOinfo.getContent(), "Content should be set to updated value");

        // Verify that save was called on the repository
        verify(ssoinfoRepository, times(1)).save(any(SSOinfo.class));
    }

    @Test
    public void testCreateSSOinfo() {
        // Mock the save method to return the newly created SSOinfo object
        when(ssoinfoRepository.save(any(SSOinfo.class))).thenAnswer(invocation -> {
            SSOinfo newSsoinfo = invocation.getArgument(0);
            newSsoinfo.setInfoId(1L); // Simulating the assignment of a generated ID
            return newSsoinfo;
        });

        // Call the method to test
        SSOinfo createdSSOinfo = ssoinfoService.createSSOinfo("New Content");

        // Assert the SSOinfo was created
        assertNotNull(createdSSOinfo, "Created SSOinfo should not be null");
        assertEquals("New Content", createdSSOinfo.getContent(), "Content should match the newly created value");

        // Verify that save was called on the repository
        verify(ssoinfoRepository, times(1)).save(any(SSOinfo.class));
    }

}
