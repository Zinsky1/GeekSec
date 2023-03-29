package com.example.geekseq.controller;

import com.example.geekseq.model.User;
import com.example.geekseq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home page";
    }

    @GetMapping("/logged")
    public String logged(Principal principal) {
        // принципл и юзер не одно и тоже
        User user = userService.findByUsername(principal.getName());
        return "secured part of the web site: " + user.getUsername() + ' ' + user.getEmail();
    }

    @GetMapping("/read_profile")
    public String readProfilePage() {
        return "read profile page  ";
    }

    @GetMapping("/only_admins")
    public String onlyForAdminsPage() {
        return "only for admins page ";
    }
}
