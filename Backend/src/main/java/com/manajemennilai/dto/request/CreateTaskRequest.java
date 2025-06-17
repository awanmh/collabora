// src/main/java/com/manajemennilai/dto/request/CreateTaskRequest.java
package com.manajemennilai.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.Instant;

@Data
public class CreateTaskRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Deadline is required")
    private Instant deadline;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assignedToId;

    private boolean isMilestone;
}
