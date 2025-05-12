// Lecturer.java (Model Dosen)

package com.manajemennilai.model;

import jakarta.persistence.Entity;

/**
 * Entitas untuk dosen, mewarisi User.
 */
@Entity
public class Lecturer extends User {

    private String lecturerId;

    // Getters and setters
    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}