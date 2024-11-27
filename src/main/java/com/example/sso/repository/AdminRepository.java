package com.example.sso.repository;

import com.example.sso.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>  {// Integer as placeholder
    Optional<Admin> findbyUsername(String username);
}
