// User.java (Model Pengguna)

package com.manajemennilai.model;

import jakarta.persistence.*;

<<<<<<< HEAD
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
=======
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class untuk pengguna (mahasiswa atau dosen).
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // Getters dan setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
=======
    private String username;
    private String password;
    private String role; // STUDENT atau LECTURER

    @ManyToMany(mappedBy = "members")
    private List<Project> projects = new ArrayList<>();

    // Getters and setters
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}


>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
