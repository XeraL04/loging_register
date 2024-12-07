package com.login.login.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserDashboard {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER')")
    public String getUserDashboard() {
        return "Welcome to the User Dashboard!";
    }
}
