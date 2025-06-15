// src/main/java/com/manajemennilai/service/AuthService.java
package com.manajemennilai.service;

import com.manajemennilai.dto.request.LoginRequest;
import com.manajemennilai.dto.request.RegisterRequest;
import com.manajemennilai.dto.response.AuthResponse;
import com.manajemennilai.exception.errors.AuthenticationFailedException;
import com.manajemennilai.exception.errors.ValidationException;
import com.manajemennilai.model.Lecturer;
import com.manajemennilai.model.Student;
import com.manajemennilai.model.User;
import com.manajemennilai.repository.LecturerRepository; // 1. PASTIKAN REPO INI DI-IMPORT
import com.manajemennilai.repository.StudentRepository;  // 2. PASTIKAN REPO INI DI-IMPORT
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
import org.springframework.transaction.annotation.Transactional; // 3. TAMBAHKAN ANOTASI TRANSAKSIONAL

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
    private StudentRepository studentRepository; // 4. INJECT STUDENT REPOSITORY

    @Autowired
    private LecturerRepository lecturerRepository; // 5. INJECT LECTURER REPOSITORY

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Metode login() Anda sudah benar, tidak perlu diubah.
    public AuthResponse login(LoginRequest request) {
        // ... (kode login Anda tetap sama)
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


    @Transactional // 6. GUNAKAN @Transactional
    public AuthResponse register(RegisterRequest request) {
        logger.info("Mencoba registrasi untuk username: {}", request.getUsername());

        // Validasi input tetap sama
        if (request.getUsername() == null || request.getUsername().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty() ||
                request.getRole() == null || request.getRole().trim().isEmpty()) {
            logger.error("Username, password, atau role tidak boleh kosong");
            throw new ValidationException("Username, password, dan role wajib diisi");
        }

        String requestedRole = request.getRole().toUpperCase();
        if ("STUDENT".equals(requestedRole)) {
            if (request.getStudentId() == null || request.getStudentId().trim().isEmpty()) {
                throw new ValidationException("ID Mahasiswa wajib diisi untuk role STUDENT");
            }
        } else if ("LECTURER".equals(requestedRole)) {
            if (request.getLecturerId() == null || request.getLecturerId().trim().isEmpty()) {
                throw new ValidationException("ID Dosen wajib diisi untuk role LECTURER");
            }
        } else {
            throw new ValidationException("Role tidak valid. Pilih 'STUDENT' atau 'LECTURER'.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username sudah ada");
        }

        // Logika utama
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(User.Role.valueOf(requestedRole));

            User savedUser = userRepository.save(user); // Simpan user terlebih dahulu
            logger.info("Pengguna berhasil disimpan dengan id: {}", savedUser.getId());

            // ==== PERBAIKAN UTAMA ADA DI SINI ====
            if ("STUDENT".equals(requestedRole)) {
                Student student = new Student();
                student.setUser(savedUser); // Kaitkan dengan user yang baru dibuat
                student.setStudentId(request.getStudentId());
                studentRepository.save(student); // 7. SIMPAN ENTITAS STUDENT!
                logger.info("Entitas Student berhasil dibuat untuk user: {}", savedUser.getUsername());

            } else { // LECTURER
                Lecturer lecturer = new Lecturer();
                lecturer.setUser(savedUser); // Kaitkan dengan user yang baru dibuat
                lecturer.setLecturerId(request.getLecturerId());
                lecturerRepository.save(lecturer); // 8. SIMPAN ENTITAS LECTURER!
                logger.info("Entitas Lecturer berhasil dibuat untuk user: {}", savedUser.getUsername());
            }

            CustomUserDetails userDetails = new CustomUserDetails(savedUser);
            String token = jwtUtils.generateToken(userDetails);
            logger.info("JWT berhasil dibuat untuk pengguna baru: {}", savedUser.getUsername());

            return new AuthResponse(token, user.getUsername(), user.getRole().toString());

        } catch (Exception e) {
            logger.error("Terjadi kesalahan tak terduga saat registrasi untuk username: {}", request.getUsername(), e);
            // Throwing a new exception to ensure transaction rollback
            throw new RuntimeException("Gagal mendaftarkan pengguna baru.", e);
        }
    }
}
