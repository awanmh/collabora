// AuthService.java

package com.manajemennilai.service;

import com.manajemennilai.dto.request.LoginRequest;
import com.manajemennilai.dto.request.RegisterRequest;
import com.manajemennilai.dto.response.AuthResponse;
import com.manajemennilai.exception.errors.AuthenticationFailedException;
import com.manajemennilai.exception.errors.ValidationException;
import com.manajemennilai.model.Lecturer;
import com.manajemennilai.model.Student;
import com.manajemennilai.model.User;
import com.manajemennilai.repository.UserRepository;
import com.manajemennilai.security.CustomUserDetails;
import com.manajemennilai.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service untuk autentikasi.
 */
@Service
public class  AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthResponse login(LoginRequest request) {
        logger.info("Attempting login for username: {}", request.getUsername());

        // Validasi input
        if (request.getUsername() == null || request.getPassword() == null) {
            logger.error("Username or password is null");
            throw new ValidationException("Username and password are required");
        }

        try {
            // Autentikasi pengguna
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Authentication successful for username: {}", request.getUsername());

            // Ambil entitas User
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> {
                        logger.error("User not found after authentication: {}", request.getUsername());
                        return new AuthenticationFailedException("Invalid username or password");
                    });

            // Buat UserDetails untuk JWT
            CustomUserDetails userDetails = new CustomUserDetails(user);
            String token = jwtUtils.generateToken(userDetails);
            logger.info("JWT generated for username: {}", request.getUsername());

            return new AuthResponse(token, user.getUsername(), user.getRole());
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for username: {}. Invalid credentials", request.getUsername());
            throw new AuthenticationFailedException("Invalid username or password", e);
        } catch (Exception e) {
            logger.error("Unexpected error during login for username: {}", request.getUsername(), e);
            throw new RuntimeException("An unexpected error occurred during login", e);
        }
    }

    public AuthResponse register(RegisterRequest request) {
        logger.info("Attempting registration for username: {}", request.getUsername());

        // Validasi input
        if (request.getUsername() == null || request.getPassword() == null || request.getRole() == null) {
            logger.error("Username, password, or role is null");
            throw new ValidationException("Username, password, and role are required");
        }

        // Validasi studentId atau lecturerId berdasarkan role
        if ("STUDENT".equalsIgnoreCase(request.getRole()) && request.getStudentId() == null) {
            logger.error("Student ID is required for role STUDENT");
            throw new ValidationException("Student ID is required for student role");
        }
        if ("LECTURER".equalsIgnoreCase(request.getRole()) && request.getLecturerId() == null) {
            logger.error("Lecturer ID is required for role LECTURER");
            throw new ValidationException("Lecturer ID is required for lecturer role");
        }

        // Cek apakah username sudah ada
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.error("Username already exists: {}", request.getUsername());
            throw new ValidationException("Username already exists");
        }

        try {
            // Buat entitas User berdasarkan role
            User user;
            if ("STUDENT".equalsIgnoreCase(request.getRole())) {
                user = new Student();
                ((Student) user).setStudentId(request.getStudentId());
            } else if ("LECTURER".equalsIgnoreCase(request.getRole())) {
                user = new Lecturer();
                ((Lecturer) user).setLecturerId(request.getLecturerId());
            } else {
                logger.error("Invalid role: {}", request.getRole());
                throw new ValidationException("Invalid role");
            }

            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole().toUpperCase());

            // Simpan user ke database
            userRepository.save(user);
            logger.info("User saved: {}", request.getUsername());

            // Buat UserDetails untuk JWT
            CustomUserDetails userDetails = new CustomUserDetails(user);
            String token = jwtUtils.generateToken(userDetails);
            logger.info("JWT generated for new user: {}", request.getUsername());

            return new AuthResponse(token, user.getUsername(), user.getRole());
        } catch (Exception e) {
            logger.error("Unexpected error during registration for username: {}", request.getUsername(), e);
            throw new RuntimeException("An unexpected error occurred during registration", e);
        }
    }
}