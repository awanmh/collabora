// NotificationResponse.java (DTO untuk respons notifikasi)

package com.manajemennilai.dto.response;

/**
 * DTO untuk respons notifikasi.
 */
public class NotificationResponse {

    private Long id;
    private String message;
    private Long userId;
    private boolean isRead;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}