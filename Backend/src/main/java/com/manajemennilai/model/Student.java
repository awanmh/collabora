// Student.java (Model Mahasiswa)

package com.manajemennilai.model;

import jakarta.persistence.*;

/**
 * Entitas untuk mahasiswa.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    private Long id;

    @Column(name = "student_id", unique = true, nullable = false)
    private String studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}