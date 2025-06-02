// AuthController.java

package com.manajemennilai.controller;

<<<<<<< HEAD
import com.manajemennilai.dto.request.LoginRequest;
import com.manajemennilai.dto.response.AuthResponse;
import com.manajemennilai.dto.request.RegisterRequest;
import com.manajemennilai.service.AuthService;
import jakarta.validation.Valid;
=======
import com.manajemennilai.dto.request.LoginRequest; // Impor LoginRequest
import com.manajemennilai.dto.response.AuthResponse;
import com.manajemennilai.dto.request.RegisterRequest;
import com.manajemennilai.service.AuthService;
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
=======
import org.springframework.web.bind.annotation.RequestMapping; // Impor RequestMapping
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Penempatan RequestMapping yang benar
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
<<<<<<< HEAD
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }
}
=======
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest)); // Menggunakan LoginRequest
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }
}
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
