package com.example.sso.controller;

import com.example.sso.model.Department;
import com.example.sso.model.MembershipType;
import com.example.sso.model.User;
import com.example.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Create Member with membership type and department
    @PostMapping("/create-member")
    public ResponseEntity<?> createMember(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        String membershipTypeStr = request.get("membershipType");
        String departmentStr = request.get("department");

        try {
            MembershipType membershipType = MembershipType.valueOf(membershipTypeStr.toUpperCase());
            Department department = null;

            if (membershipType == MembershipType.ACTIVE) {
                if (departmentStr == null || departmentStr.isEmpty()) {
                    throw new RuntimeException("Active members must select a department.");
                }
                department = Department.valueOf(departmentStr.toUpperCase());
            } else if (membershipType == MembershipType.PASSIVE && departmentStr != null) {
                throw new RuntimeException("Passive members cannot select a department.");
            }

            User member = userService.createMember(username, email, password, membershipType, department);
            return ResponseEntity.ok(member);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Invalid membership type or department.");
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
            return ResponseEntity.ok(Map.of("role", user.getRole()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());  // Return the error message
        }
    }
}
