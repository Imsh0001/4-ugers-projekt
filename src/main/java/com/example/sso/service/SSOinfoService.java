package com.example.sso.service;

import com.example.sso.model.SSOinfo;
import com.example.sso.repository.SSOinfoRepository;
import org.springframework.stereotype.Service;

@Service
public class SSOinfoService {

    private final SSOinfoRepository ssoinfoRepository;

    public SSOinfoService(SSOinfoRepository ssoinfoRepository) {
        this.ssoinfoRepository = ssoinfoRepository;
    }


    public SSOinfo getSSOinfo() {

        return ssoinfoRepository.findAll().stream().findFirst().orElse(null);
    }

    public SSOinfo updateSSOinfo(String newContent) {
        SSOinfo ssoinfo = getSSOinfo();
        if (ssoinfo == null) {
            ssoinfo = new SSOinfo();
        }
        ssoinfo.setContent(newContent);
        return ssoinfoRepository.save(ssoinfo);
    }


    public SSOinfo createSSOinfo(String content) {
        SSOinfo ssoinfo = new SSOinfo(content);
        return ssoinfoRepository.save(ssoinfo);
    }
}
