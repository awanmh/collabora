// ProjectResponse.java (DTO untuk respons proyek)

package com.manajemennilai.dto.response;

import java.util.List;

/**
 * DTO untuk respons proyek.
 */
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private List<Long> memberIds;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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