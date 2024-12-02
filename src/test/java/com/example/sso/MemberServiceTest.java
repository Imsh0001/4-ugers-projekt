package com.example.sso;


import com.example.sso.model.Member;
import com.example.sso.repository.MemberRepository;
import com.example.sso.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMemberById_ShouldReturnMember_WhenExists() {
        // Arrange
        Member member = new Member();
        member.setMemberId(1L);
        member.setName("John");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // Act
        Member result = memberService.getMemberById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getMemberId());
        assertEquals("John", result.getName());
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void getMemberById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.getMemberById(1L);
        });

        assertEquals("Member not found with ID: 1", exception.getMessage());
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void createMember_ShouldSaveAndReturnMember() {
        // Arrange
        Member member = new Member();
        member.setName("Jane");

        when(memberRepository.save(member)).thenReturn(member);

        // Act
        Member result = memberService.createMember(member);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.getName());
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void getAllMembers_ShouldReturnAllMembers() {
        // Arrange
        List<Member> members = Arrays.asList(new Member(), new Member());
        when(memberRepository.findAll()).thenReturn(members);

        // Act
        List<Member> result = memberService.getAllMembers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void updateMember_ShouldUpdateAndReturnMember_WhenExists() {
        // Arrange
        Member existingMember = new Member();
        existingMember.setMemberId(1L);
        existingMember.setName("Old Name");

        Member updatedMember = new Member();
        updatedMember.setName("New Name");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(existingMember));
        when(memberRepository.save(existingMember)).thenReturn(existingMember);

        // Act
        Member result = memberService.updateMember(1L, updatedMember);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).save(existingMember);
    }

    @Test
    void updateMember_ShouldThrowException_WhenNotFound() {
        // Arrange
        Member updatedMember = new Member();
        updatedMember.setName("New Name");

        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.updateMember(1L, updatedMember);
        });

        assertEquals("Member not found with ID: 1", exception.getMessage());
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void deleteMember_ShouldDeleteMember_WhenExists() {
        // Arrange
        when(memberRepository.existsById(1L)).thenReturn(true);

        // Act
        memberService.deleteMember(1L);

        // Assert
        verify(memberRepository, times(1)).existsById(1L);
        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteMember_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(memberRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.deleteMember(1L);
        });

        assertEquals("Member not found with ID: 1", exception.getMessage());
        verify(memberRepository, times(1)).existsById(1L);
        verify(memberRepository, never()).deleteById(1L);
    }
}
