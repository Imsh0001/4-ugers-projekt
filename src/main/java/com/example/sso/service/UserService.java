package com.example.sso.service;

import com.example.sso.model.Department;
import com.example.sso.model.MembershipType;
import com.example.sso.model.StudyField;
import com.example.sso.model.User;
import com.example.sso.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to check if username exists
    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Method to check if email exists
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Create a new admin user
    public User createAdmin(String username, String email, String password) {
        if (usernameExists(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (emailExists(email)) {
            throw new RuntimeException("Email already exists");
        }
        User admin = new User(username, email, password, "ROLE_ADMIN", null, null, null, null, null);
        return userRepository.save(admin);
    }

    // Create a new member user
    public User createMember(String username, String email, String password, MembershipType membershipType, Department department, String education, StudyField studyField, String educationLevel) {
        if (usernameExists(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (emailExists(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Validate membership type and department
        if (membershipType == MembershipType.ACTIVE && department == null) {
            throw new RuntimeException("Active members must select a department.");
        }
        if (membershipType == MembershipType.PASSIVE && department != null) {
            throw new RuntimeException("Passive members cannot select a department.");
        }

        User member = new User(username, email, password, "ROLE_MEMBER", membershipType, department, education, studyField, educationLevel);
        return userRepository.save(member);
    }

    // Authenticate user by username or email and password
    public User authenticate(String usernameOrEmail, String password) {
        User user = userRepository.findByUsername(usernameOrEmail)
                .orElseGet(() -> userRepository.findByEmail(usernameOrEmail)
                        .orElseThrow(() -> new RuntimeException("User not found")));
        System.out.println("Fetched User: " + user);

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        System.out.println("Authenticated User: " + user);
        return user;
    }
}
