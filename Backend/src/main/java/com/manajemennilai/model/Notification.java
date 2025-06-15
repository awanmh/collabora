// src/main/java/com/manajemennilai/model/Notification.java
package com.manajemennilai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entitas untuk notifikasi.
 */
@Entity
@Table(name = "notifications") // 1. Menambahkan 's' agar cocok dengan schema.sql
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // 2. Menambahkan spesifikasi kolom
    private String message;

    @Column(name = "is_read", nullable = false) // 3. Memetakan ke nama kolom 'is_read'
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 4. Mendefinisikan nama foreign key
    private User user;

    // Getter dan setter manual bisa dihapus karena sudah digantikan oleh @Getter dan @Setter
}
