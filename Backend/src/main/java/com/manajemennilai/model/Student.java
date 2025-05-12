// Student.java (Model Mahasiswa)

package com.manajemennilai.model;

import jakarta.persistence.Entity;

/**
 * Entitas untuk mahasiswa, mewarisi User.
 */
@Entity
public class Student extends User {

    private String studentId;

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}