// CreateTaskRequest.java (DTO untuk pembuatan tugas)

package com.manajemennilai.dto.request;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonFormat;
=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO untuk request pembuatan tugas.
 */
public class CreateTaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Deadline is required")
<<<<<<< HEAD
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Format ISO-8601
=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    private LocalDateTime deadline;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assignedToId;

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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }
}