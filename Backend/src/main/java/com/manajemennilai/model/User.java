// User.java (Model Pengguna)

package com.manajemennilai.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Kelas untuk pengguna (mahasiswa atau dosen).
 */
@Entity
@Table(name = "user") // Sesuaikan dengan schema.sql
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Tambahkan untuk mendukung ENUM di database
    @Column(nullable = false)
    private Role role;

    @ManyToMany(mappedBy = "members")
    private List<Project> projects = new ArrayList<>();

    // Enum untuk role
    public enum Role {
        STUDENT, LECTURER
    }

    // Getters dan setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}