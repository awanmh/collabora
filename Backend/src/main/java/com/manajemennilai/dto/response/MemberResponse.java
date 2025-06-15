// src/main/java/com/manajemennilai/dto/response/MemberResponse.java
package com.manajemennilai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Menggunakan Lombok untuk mengurangi boilerplate code
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private Long id; // ID dari tabel Student atau Lecturer
    private UserDetails user;
    private String studentId; // NIM
    private String lecturerId; // NIDN

    // Nested class untuk mencocokkan struktur { user: { name: "..." } } di frontend
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDetails {
        private String name;
    }
}
