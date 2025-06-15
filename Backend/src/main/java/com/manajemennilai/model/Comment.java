// src/main/java/com/manajemennilai/model/Comment.java
package com.manajemennilai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entitas untuk komentar.
 */
@Entity
@Table(name = "comments") // 1. Menambahkan 's' agar cocok dengan schema.sql
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false) // 2. Menambahkan spesifikasi kolom
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false) // 3. Mendefinisikan nama foreign key
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false) // 3. Mendefinisikan nama foreign key
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id") // 3. Mendefinisikan nama foreign key
    private Comment parentComment;

    // Getter dan setter manual bisa dihapus karena sudah digantikan oleh @Getter dan @Setter
}
