package com.example.sso.controller;

import com.example.sso.model.Admin;
import com.example.sso.model.Member;
import com.example.sso.service.AdminService;
import com.example.sso.service.EventService;
import com.example.sso.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final EventService eventService;

    private final AdminService adminService;

    @Autowired
    public AdminController(MemberService memberService, EventService eventService, AdminService adminService) {
        this.memberService = memberService;
        this.eventService = eventService;
        this.adminService = adminService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestParam String username, @RequestParam String password) {
        Optional<Admin> admin = adminService.verifyAdminCredentials(username, password);
        if (admin.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }
    @PostMapping("/members")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member newMember = memberService.saveMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok("Member deleted successfully.");
    }
    @GetMapping("/events")
    public ResponseEntity<?> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
    @PostMapping("/add")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin newAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAdmin);
    }






}
