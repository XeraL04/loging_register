package com.login.login.initializer;

import com.login.login.model.Role;
import com.login.login.model.User;
import com.login.login.repository.RoleRepository;
import com.login.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("USER") == null) {
            roleRepository.save(new Role("USER"));
        }
        if (roleRepository.findByName("ADMIN") == null) {
            roleRepository.save(new Role("ADMIN"));
        }

        // Create the first user with ADMIN role if no user exists
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN");
            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setMobile("0123456789");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Use a strong password!
            adminUser.getRoles().add(adminRole); // Add ADMIN role
            userRepository.save(adminUser);
        }
    }
}