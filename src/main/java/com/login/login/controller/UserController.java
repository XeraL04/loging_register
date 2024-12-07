package com.login.login.controller;

import com.login.login.dto.LoginDto;
import com.login.login.dto.RegistrationDto;
import com.login.login.model.User;
import com.login.login.security.jwt.JwtUtils;
import com.login.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationDto registrationDTO) {
        userService.registerUser(registrationDTO);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto){
        User user = userService.authenticateUser(loginDto.getEmail(), loginDto.getPassword());

        if (user == null){
            throw new RuntimeException("Invalid Credentials");
        }

        //generate JWT
        String token = jwtUtils.generateToken(user.getEmail());
        return "Bearer " + token;
    }


}