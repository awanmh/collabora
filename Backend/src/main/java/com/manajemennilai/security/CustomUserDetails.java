// UserDetailsCustom.java // Menggunakan nama file yang lebih umum (UserDetailsCustom.java)

package com.manajemennilai.security;

import com.manajemennilai.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * CustomUserDetails adalah implementasi dari UserDetails,
 * digunakan oleh Spring Security untuk melakukan otentikasi dan otorisasi
 * berdasarkan entitas User di database.
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Mengembalikan otoritas (role) pengguna.
     * Role akan diawali dengan "ROLE_" sesuai konvensi Spring Security.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())
        );
    }

    /**
     * Mengembalikan password terenkripsi pengguna.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Mengembalikan username pengguna (biasanya email atau username unik).
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Menandakan apakah akun pengguna tidak expired.
     * Dalam implementasi ini, selalu mengembalikan true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Menandakan apakah akun pengguna tidak terkunci.
     * Dalam implementasi ini, selalu mengembalikan true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Menandakan apakah kredensial (password) pengguna tidak expired.
     * Dalam implementasi ini, selalu mengembalikan true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Menandakan apakah akun pengguna aktif (enabled).
     * Dalam implementasi ini, selalu mengembalikan true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Mengembalikan objek User asli jika diperlukan untuk mengakses properti lain yang tidak ada di UserDetails.
     */
    public User getUser() {
        return user;
    }
}