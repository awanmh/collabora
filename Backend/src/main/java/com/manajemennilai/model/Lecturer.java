// Lecturer.java (Model Dosen)

package com.manajemennilai.model;

<<<<<<< HEAD
import jakarta.persistence.*;

@Entity
@Table(name = "lecturer")
public class Lecturer extends User {

    @Column(name = "lecturer_id", unique = true, nullable = false)
=======
import jakarta.persistence.Entity;

/**
 * Entitas untuk dosen, mewarisi User.
 */
@Entity
public class Lecturer extends User {

>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    private String lecturerId;

    // Getters and setters
    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}