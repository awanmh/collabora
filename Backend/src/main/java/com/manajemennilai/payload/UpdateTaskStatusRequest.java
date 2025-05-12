// UpdateTaskStatusRequest.java

package com.manajemennilai.payload;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO untuk request pembaruan status tugas.
 */
public class UpdateTaskStatusRequest {

    @NotBlank(message = "Status is required")
    private String status;

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
