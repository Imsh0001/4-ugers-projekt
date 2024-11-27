package com.example.sso.model;


import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "admin_id")
    private int adminId;


    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;


    public Admin(String username, String password){
        this.username = username;
        this.password = password;
    }


    public Admin() {

    }

    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }


}
