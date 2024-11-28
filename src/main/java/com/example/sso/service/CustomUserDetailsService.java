package com.example.sso.service;

import com.example.sso.model.Admin;
import com.example.sso.model.Member;
import com.example.sso.repository.AdminRepository;
import com.example.sso.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return new User(admin.get().getUsername(), admin.get().getPassword(), getAdminAuthorities());
        }

        Optional<Member> member = memberRepository.findByEmail(username);
        if (member.isPresent()) {
            return new User(member.get().getEmail(), member.get().getPassword(), getMemberAuthorities());
        }

        throw new UsernameNotFoundException("User not found");
    }

    private Collection<? extends GrantedAuthority> getAdminAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private Collection<? extends GrantedAuthority> getMemberAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_MEMBER"));
    }
}
