// src/main/java/com/manajemennilai/model/User.java
package com.manajemennilai.model;

import com.fasterxml.jackson.annotation.JsonBackReference; // 1. Import anotasi
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Relasi dibalik dari Project
    @ManyToMany(mappedBy = "members")
    @JsonBackReference // 2. Tambahkan anotasi ini
    private List<Project> projects = new ArrayList<>();

    public enum Role {
        STUDENT, LECTURER
    }

    // Hapus getter/setter manual jika sudah menggunakan Lombok
}
