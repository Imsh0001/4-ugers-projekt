package com.example.sso.config;

import com.example.sso.model.Department;
import com.example.sso.model.MembershipType;
import com.example.sso.model.StudyField;
import com.example.sso.repository.UserRepository;
import com.example.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {

            userService.createAdmin("admin", "admin@example.com", "adminpass");


            userService.createMember("activeMember", "active@example.com", "activepass", MembershipType.ACTIVE, Department.FUNDING,"Computer Science", StudyField.ENGINEERING_SCIENCES,"Bachelor");
            userService.createMember("passiveMember", "passive@example.com", "passivepass", MembershipType.PASSIVE, null, null, null,null);
        }
    }
}
