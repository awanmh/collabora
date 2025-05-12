// AuthController.java

package com.manajemennilai.controller;

import com.manajemennilai.dto.request.LoginRequest; // Impor LoginRequest
import com.manajemennilai.dto.response.AuthResponse;
import com.manajemennilai.dto.request.RegisterRequest;
import com.manajemennilai.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // Impor RequestMapping
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Penempatan RequestMapping yang benar
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest)); // Menggunakan LoginRequest
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }
}
