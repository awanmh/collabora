// LoginRequest.java (DTO untuk login)

package com.manajemennilai.dto.request;

<<<<<<< HEAD
public class LoginRequest {
    private String username;
=======
import jakarta.validation.constraints.NotBlank;

/**
 * DTO untuk request login.
 */
public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    private String password;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}