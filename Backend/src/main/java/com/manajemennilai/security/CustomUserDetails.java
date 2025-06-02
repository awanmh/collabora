<<<<<<< HEAD
//UserDetailsDetails.java
=======
//UserDetailsCustom.java
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e

package com.manajemennilai.security;

import com.manajemennilai.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
<<<<<<< HEAD
 * CustomUserDetails adalah implementasi dari UserDetails,
 * digunakan oleh Spring Security untuk melakukan otentikasi dan otorisasi
 * berdasarkan entitas User di database.
=======
 * Implementasi UserDetails untuk entitas User.
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

<<<<<<< HEAD
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
=======
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    @Override
    public String getPassword() {
        return user.getPassword();
    }

<<<<<<< HEAD
    /**
     * Mengembalikan username pengguna (biasanya email atau username unik).
     */
=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    @Override
    public String getUsername() {
        return user.getUsername();
    }

<<<<<<< HEAD
    /**
     * Menandakan akun tidak expired.
     */
=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

<<<<<<< HEAD
    /**
     * Menandakan akun tidak dikunci.
     */
=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

<<<<<<< HEAD
    /**
     * Menandakan kredensial (password) tidak expired.
     */
=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

<<<<<<< HEAD
    /**
     * Menandakan akun aktif (enabled).
     */
=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    @Override
    public boolean isEnabled() {
        return true;
    }

<<<<<<< HEAD
    /**
     * Mengembalikan objek User asli jika diperlukan.
     */
    public User getUser() {
        return user;
    }
}
=======
    public User getUser() {
        return user;
    }
}
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
