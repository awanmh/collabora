// AuthResponse.java (DTO untuk respons autentikasi)

package com.manajemennilai.dto.response;

/**
 * DTO untuk respons autentikasi (mengembalikan token JWT, username, dan role).
 */
public class AuthResponse {

    private String token;
    private String username; // Ditambahkan
    private String role;     // Ditambahkan

    public AuthResponse(String token, String username, String role) { // Konstruktor disesuaikan
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() { // Getter baru
        return username;
    }

    public void setUsername(String username) { // Setter baru
        this.username = username;
    }

    public String getRole() { // Getter baru
        return role;
    }

    public void setRole(String role) { // Setter baru
        this.role = role;
    }
}