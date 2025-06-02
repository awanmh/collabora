// AuthResponse.java (DTO untuk respons autentikasi)

package com.manajemennilai.dto.response;

<<<<<<< HEAD
public class AuthResponse {
    private String token;
    private String message;

    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
=======
/**
 * DTO untuk respons autentikasi (mengembalikan token JWT).
 */
public class AuthResponse {

    private String token;
    private String username;
    private String role;

    public AuthResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

<<<<<<< HEAD
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
=======
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    }
}