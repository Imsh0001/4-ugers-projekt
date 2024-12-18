package com.example.sso.controller;

import com.example.sso.model.Department;
import com.example.sso.model.MembershipType;
import com.example.sso.model.StudyField;
import com.example.sso.model.User;
import com.example.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create Admin with email support
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        try {
            User admin = userService.createAdmin(username, email, password);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/create-member")
    public ResponseEntity<?> createMember(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        String membershipTypeStr = request.get("membershipType");
        String departmentStr = request.get("department");
        String education = request.get("education");
        String studyFieldStr = request.get("studyField");
        String educationLevel = request.get("educationLevel");

        try {
            // Konverter membershipType til enum
            MembershipType membershipType = MembershipType.valueOf(membershipTypeStr.toUpperCase());
            Department department = null;
            StudyField studyField = null;

            // Valider medlemskabstype og afdeling
            if (membershipType == MembershipType.ACTIVE) {
                if (departmentStr == null || departmentStr.isEmpty()) {
                    throw new RuntimeException("Active members must select a department.");
                }
                department = Department.valueOf(departmentStr.toUpperCase());

                // StudyField er kun relevant for aktive medlemmer
                if (studyFieldStr != null && !studyFieldStr.isEmpty()) {
                    studyField = StudyField.valueOf(studyFieldStr.toUpperCase());
                }
            } else if (membershipType == MembershipType.PASSIVE) {
                if (departmentStr != null) {
                    throw new RuntimeException("Passive members cannot select a department.");
                }
                if (studyFieldStr != null && !studyFieldStr.isEmpty()) {
                    throw new RuntimeException("Passive members cannot select a study field.");
                }
            }

            // Opret medlem via UserService
            User member = userService.createMember(username, email, password, membershipType, department, education, studyField, educationLevel);
            return ResponseEntity.ok(member);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Invalid membership type, department, or study field.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        try {
            User user = userService.authenticate(username, password);

            Map<String, Object> response = new HashMap<>();
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());

            // Kun nødvendige oplysninger returneres
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}