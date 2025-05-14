// AuthService.java

package com.manajemennilai.service;

import com.manajemennilai.dto.request.LoginRequest;
import com.manajemennilai.dto.request.RegisterRequest;
import com.manajemennilai.dto.response.AuthResponse;
import com.manajemennilai.model.Student;
import com.manajemennilai.model.Lecturer;
import com.manajemennilai.model.User;
import com.manajemennilai.repository.UserRepository;
import com.manajemennilai.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest loginRequest) {
        try {
            logger.info("Attempting login for user: {}", loginRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            logger.info("Authentication successful for user: {}", loginRequest.getUsername());
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtUtils.generateToken(userDetails);
            logger.info("Token generated for user: {}", loginRequest.getUsername());
            return new AuthResponse(jwtToken, "Login successful");
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}. Error: {}", loginRequest.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Invalid username or password");
        } catch (Exception e) {
            logger.error("Unexpected error during login for user: {}. Error: {}", loginRequest.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        logger.info("Registering new user: {}", registerRequest.getUsername());
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            logger.warn("Username already exists: {}", registerRequest.getUsername());
            throw new RuntimeException("Username already exists");
        }

        User user;
        String role;

        String userType = registerRequest.getRole() != null ? registerRequest.getRole().toUpperCase() : "STUDENT";

        if ("LECTURER".equals(userType)) {
            Lecturer lecturer = new Lecturer();
            lecturer.setLecturerId("LEC" + System.currentTimeMillis()); // Contoh ID sementara
            user = lecturer;
            role = "LECTURER";
        } else {
            Student student = new Student();
            student.setStudentId("STU" + System.currentTimeMillis()); // Contoh ID sementara
            user = student;
            role = "STUDENT";
        }

        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(role);

        try {
            userRepository.save(user);
            logger.info("User saved successfully: {}", registerRequest.getUsername());
        } catch (Exception e) {
            logger.error("Failed to save user: {}. Error: {}", registerRequest.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole())
                .build();

        String jwtToken;
        try {
            jwtToken = jwtUtils.generateToken(userDetails);
            logger.info("Token generated for user: {}", registerRequest.getUsername());
        } catch (Exception e) {
            logger.error("Failed to generate JWT token for user: {}. Error: {}", registerRequest.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Failed to generate token: " + e.getMessage(), e);
        }

        logger.info("Registration successful for user: {}", registerRequest.getUsername());
        return new AuthResponse(jwtToken, "Registration successful");
    }
}