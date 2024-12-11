package com.example.sso.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {
    // Getters og setters
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String department;  // Tilføjet afdeling
    private String membershipType;  // Hvis du vil bruge membershipType i signup, ellers kan den sættes statisk

}
