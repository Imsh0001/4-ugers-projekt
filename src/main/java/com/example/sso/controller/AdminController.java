package com.example.sso.controller;

import com.example.sso.model.Member;
import com.example.sso.service.EventService;
import com.example.sso.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final EventService eventService;

    @Autowired
    public AdminController(MemberService memberService, EventService eventService) {
        this.memberService = memberService;
        this.eventService = eventService;
    }

}
