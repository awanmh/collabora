// CreateProjectRequest.java (DTO untuk pembuatan proyek)

package com.manajemennilai.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * DTO untuk request pembuatan proyek.
 */
public class CreateProjectRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private List<Long> memberIds;

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}