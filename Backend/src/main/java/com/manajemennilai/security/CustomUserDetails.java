//UserDetailsDetails.java

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
     * Menandakan akun tidak expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Menandakan akun tidak dikunci.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Menandakan kredensial (password) tidak expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Menandakan akun aktif (enabled).
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Mengembalikan objek User asli jika diperlukan.
     */
    public User getUser() {
        return user;
    }
}
