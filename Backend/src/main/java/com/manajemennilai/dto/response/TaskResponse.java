// src/main/java/com/manajemennilai/dto/response/TaskResponse.java
package com.manajemennilai.dto.response;

import lombok.Data;
import java.time.Instant;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Instant deadline;
    private String status;
    private Long projectId;
    private Long assignedToId;
    private boolean isMilestone;
}
