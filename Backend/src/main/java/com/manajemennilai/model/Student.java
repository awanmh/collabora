// Student.java (Model Mahasiswa)

package com.manajemennilai.model;

<<<<<<< HEAD
import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student extends User {

    @Column(name = "student_id", unique = true, nullable = false)
=======
import jakarta.persistence.Entity;

/**
 * Entitas untuk mahasiswa, mewarisi User.
 */
@Entity
public class Student extends User {

>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    private String studentId;

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}