package com.example.sso.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Member implements UserDetails {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Setter
    @Getter
    private String name;
    @Getter
    @Setter
    private String lastName;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String password;
    @Getter
    @Setter
    private String department;
    @Getter
    @Setter
    private String membershipType;

    @OneToMany(mappedBy = "member")
    private Set<Booking> bookings;

    public Member() {}

    // UserDetails implementation

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Returner brugerens roller som authorities. Du kan udvide dette senere.
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(() -> "ROLE_" + membershipType); // Antag at membershipType bestemmer rollen
        return authorities;
    }

    @Override
    public String getUsername() {
        return email; // Brug email som brugernavn
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
