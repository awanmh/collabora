// Lecturer.java (Model Dosen)

package com.manajemennilai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lecturer")
public class Lecturer extends User {

    @Column(name = "lecturer_id", unique = true, nullable = false)
    private String lecturerId;

    // Getters and setters
    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}