// Lecturer.java (Model Dosen)

package com.manajemennilai.model;

import jakarta.persistence.*;

/**
 * Entitas untuk dosen.
 */
@Entity
@Table(name = "lecturers")
public class Lecturer {

    @Id
    private Long id;

    @Column(name = "lecturer_id", unique = true, nullable = false)
    private String lecturerId;

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

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}