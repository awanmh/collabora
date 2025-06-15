// src/main/java/com/manajemennilai/dto/request/CreateProjectRequest.java
package com.manajemennilai.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data; // 1. Import Lombok
import java.util.List;

/**
 * DTO untuk request pembuatan proyek.
 */
@Data // 2. Gunakan anotasi @Data untuk otomatis membuat getter, setter, dll.
public class CreateProjectRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotEmpty(message = "Member IDs are required")
    private List<Long> memberIds;

    // Semua metode getter dan setter manual bisa dihapus.
    // Metode getOwnerId() yang kosong juga sudah dihapus.
}
