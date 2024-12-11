package com.example.sso.controller;

import com.example.sso.dto.LoginRequest;
import com.example.sso.dto.SignupRequest;
import com.example.sso.model.Member;
import com.example.sso.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MemberRepository memberRepository;

    // Login functionality
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        // Autentificer brugeren
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Du kan generere et token her, eller bare returnere en succesmeddelelse
        // Hvis du bruger JWT, vil du generere og returnere tokenet her.

        return ResponseEntity.ok(Map.of("message", "Login successful!"));
    }


    @PostMapping("/auth/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody Member member) {
        System.out.println("Received signup request: " + member);

        if ("Aktiv".equals(member.getMembershipType()) &&
                (member.getDepartment() == null || member.getDepartment().isEmpty())) {
            return ResponseEntity.badRequest().body(Map.of("message", "For aktivt medlemskab skal du vælge en afdeling."));
        }

        try {
            Member newMember = new Member();
            newMember.setEmail(member.getEmail());
            newMember.setPassword(new BCryptPasswordEncoder().encode(member.getPassword()));
            newMember.setName(member.getName());
            newMember.setLastName(member.getLastName());
            newMember.setDepartment(member.getDepartment());
            newMember.setMembershipType(member.getMembershipType());

            memberRepository.save(newMember);
            System.out.println("Member saved successfully: " + newMember);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Signup successful!", "email", member.getEmail()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred while saving the member."));
        }
    }
}
