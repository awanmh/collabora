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

    public AuthResponse login(LoginRequest request) {
        logger.info("Mencoba login untuk username: {}", request.getUsername());

        if (request.getUsername() == null || request.getUsername().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            logger.error("Username atau password tidak boleh kosong");
            throw new ValidationException("Username dan password wajib diisi");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Autentikasi berhasil untuk username: {}", request.getUsername());

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> {
                        logger.error("Pengguna tidak ditemukan setelah autentikasi berhasil: {}", request.getUsername());
                        return new AuthenticationFailedException("Kredensial tidak valid");
                    });

            CustomUserDetails userDetails = new CustomUserDetails(user);
            String token = jwtUtils.generateToken(userDetails);
            logger.info("JWT berhasil dibuat untuk username: {}", request.getUsername());

            return new AuthResponse(token, user.getUsername(), user.getRole().toString());
        } catch (BadCredentialsException e) {
            logger.error("Autentikasi gagal untuk username: {}. Kredensial tidak valid", request.getUsername(), e);
            throw new AuthenticationFailedException("Username atau password tidak valid", e);
        } catch (Exception e) {
            logger.error("Terjadi kesalahan tak terduga saat login untuk username: {}", request.getUsername(), e);
            throw new RuntimeException("Terjadi kesalahan tak terduga saat login", e);
        }
    }

    public AuthResponse register(RegisterRequest request) {
        logger.info("Mencoba registrasi untuk username: {}", request.getUsername());

        if (request.getUsername() == null || request.getUsername().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty() ||
                request.getRole() == null || request.getRole().trim().isEmpty()) {
            logger.error("Username, password, atau role tidak boleh kosong");
            throw new ValidationException("Username, password, dan role wajib diisi");
        }

        String requestedRole = request.getRole().toUpperCase();

        if ("STUDENT".equals(requestedRole)) {
            if (request.getStudentId() == null || request.getStudentId().trim().isEmpty()) {
                logger.error("ID Mahasiswa wajib diisi untuk role STUDENT");
                throw new ValidationException("ID Mahasiswa wajib diisi untuk role STUDENT");
            }
        } else if ("LECTURER".equals(requestedRole)) {
            if (request.getLecturerId() == null || request.getLecturerId().trim().isEmpty()) {
                logger.error("ID Dosen wajib diisi untuk role LECTURER");
                throw new ValidationException("ID Dosen wajib diisi untuk role LECTURER");
            }
        } else {
            logger.error("Role tidak valid: {}", request.getRole());
            throw new ValidationException("Role tidak valid. Pilih 'STUDENT' atau 'LECTURER'.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.error("Username sudah ada: {}", request.getUsername());
            throw new ValidationException("Username sudah ada");
        }

        try {
            // Buat entitas User terpisah
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(User.Role.valueOf(requestedRole)); // Konversi String ke enum

            // Simpan User terlebih dahulu
            user = userRepository.save(user);
            logger.info("Pengguna berhasil disimpan: {}", request.getUsername());

            // Buat dan hubungkan Student atau Lecturer
            if ("STUDENT".equals(requestedRole)) {
                Student student = new Student();
                student.setId(user.getId());
                student.setStudentId(request.getStudentId());
                student.setUser(user);
                // Simpan student (opsional, karena hanya id yang diperlukan)
            } else { // LECTURER
                Lecturer lecturer = new Lecturer();
                lecturer.setId(user.getId());
                lecturer.setLecturerId(request.getLecturerId());
                lecturer.setUser(user);
                // Simpan lecturer (opsional)
            }

            CustomUserDetails userDetails = new CustomUserDetails(user);
            String token = jwtUtils.generateToken(userDetails);
            logger.info("JWT berhasil dibuat untuk pengguna baru: {}", request.getUsername());

            return new AuthResponse(token, user.getUsername(), user.getRole().toString());
        } catch (Exception e) {
            logger.error("Terjadi kesalahan tak terduga saat registrasi untuk username: {}", request.getUsername(), e);
            throw new RuntimeException("Terjadi kesalahan tak terduga saat registrasi", e);
        }
    }
}