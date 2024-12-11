package com.example.sso.controller;

import com.example.sso.model.MembershipType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/membership-types")
public class MembershipTypeController {

    @GetMapping
    public ResponseEntity<List<MembershipType>> getAllMembershipTypes() {
        return ResponseEntity.ok(Arrays.asList(MembershipType.values()));
    }
}
