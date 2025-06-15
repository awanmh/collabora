// src/main/java/com/manajemennilai/dto/response/TaskResponse.java
package com.manajemennilai.dto.response;

import lombok.Data; // Gunakan Lombok
import java.time.LocalDateTime;

/**
 * DTO untuk respons tugas.
 */
@Data // Menggantikan semua getter dan setter manual
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private String status;
    private Long projectId;
    private Long assignedToId;
    private boolean isMilestone; // Tambahkan field milestone
}
