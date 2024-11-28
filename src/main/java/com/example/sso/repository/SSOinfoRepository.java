package com.example.sso.repository;

import com.example.sso.model.SSOinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SSOinfoRepository extends JpaRepository<SSOinfo, Integer> {


}

