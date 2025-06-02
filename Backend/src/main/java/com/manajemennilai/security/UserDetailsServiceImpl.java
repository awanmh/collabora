package com.manajemennilai.security;

import com.manajemennilai.model.User;
import com.manajemennilai.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Memuat pengguna: {}", username); // Log informasi awal
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("Pengguna tidak ditemukan: {}", username); // Log error spesifik
                    return new UsernameNotFoundException("Pengguna tidak ditemukan: " + username);
                });
        logger.info("Pengguna ditemukan: {}, role: {}", username, user.getRole()); // Log jika pengguna ditemukan

        // Menggunakan builder pattern dari Spring Security UserDetails
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole()) // Menambahkan prefix ROLE_ sesuai konvensi Spring Security
                .build();
    }
}