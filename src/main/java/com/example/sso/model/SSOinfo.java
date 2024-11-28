package com.example.sso.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class SSOinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long InfoId;
    private String content;

    public SSOinfo() {}


    public SSOinfo(String content) {
        this.content = content;
    }

    public Long getInfoId() {
        return InfoId;
    }

    public void setInfoId(Long infoId) {
        InfoId = infoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
