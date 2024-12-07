package com.login.login.service;

import com.login.login.dto.RegistrationDto;
import com.login.login.model.Role;
import com.login.login.model.User;
import com.login.login.repository.RoleRepository;
import com.login.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public void registerUser(RegistrationDto registrationDTO) {
        // Check if email already exists
        userRepository.findByEmail(registrationDTO.getEmail()).ifPresent(user -> {
            throw new RuntimeException("Email already in use");
        });

        User user = new User();
        user.setName(registrationDTO.getName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword())); // Encode password
        user.setMobile(registrationDTO.getMobile());

        //assign a default role
        Role userRole = roleRepository.findByName("USER");

        // If the role does not exist in the database, throw an exception
        if (userRole == null) {
            throw new RuntimeException("Role 'USER' not found in the database.");
        }

        // Assign the role to the user
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));

        userRepository.save(user);
    }

    public User authenticateUser(String email, String password){
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())){
            return user;
        }
        return null;
    }

}
