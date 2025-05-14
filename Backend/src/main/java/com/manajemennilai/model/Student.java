// Student.java (Model Mahasiswa)

package com.manajemennilai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student extends User {

    @Column(name = "student_id", unique = true, nullable = false)
    private String studentId;

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}