// RegisterRequest.java (DTO untuk registrasi pengguna baru)

package com.manajemennilai.dto.request;

import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private String role;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}