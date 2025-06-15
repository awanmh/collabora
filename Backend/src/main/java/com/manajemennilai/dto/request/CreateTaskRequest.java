// src/main/java/com/manajemennilai/dto/request/CreateTaskRequest.java
package com.manajemennilai.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data; // 1. Gunakan Lombok untuk mengurangi boilerplate
import java.time.LocalDateTime;

/**
 * DTO untuk request pembuatan tugas.
 */
@Data // Menggantikan semua getter dan setter manual
public class CreateTaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Deadline is required")
    // 2. PERBAIKAN UTAMA: Sesuaikan format agar cocok dengan input browser (tanpa detik)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") 
    private LocalDateTime deadline;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assignedToId;
    
    // 3. Tambahkan field milestone agar konsisten dengan model
    private boolean isMilestone;
}
