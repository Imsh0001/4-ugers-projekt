package com.example.sso.dto;

import com.example.sso.model.Department;
import com.example.sso.model.MembershipType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private MembershipType membershipType;
    private Department department;


}
