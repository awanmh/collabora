// src/main/java/com/manajemennilai/model/Task.java
package com.manajemennilai.model;

import jakarta.persistence.*;
import lombok.Getter; // Menggunakan Lombok
import lombok.Setter; // Menggunakan Lombok
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entitas untuk tugas.
 */
@Entity
@Table(name = "tasks") // Ganti nama tabel menjadi "tasks" (plural) sesuai konvensi
@Getter // Menggunakan Lombok
@Setter // Menggunakan Lombok
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.NOT_STARTED; // Menggunakan Enum dari file terpisah

    private boolean isMilestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    
    // Semua getter dan setter manual bisa dihapus karena sudah digantikan oleh @Getter dan @Setter
}
