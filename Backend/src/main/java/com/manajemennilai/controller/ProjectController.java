// src/main/java/com/manajemennilai/controller/ProjectController.java
package com.manajemennilai.controller;

import com.manajemennilai.dto.request.CreateProjectRequest;
import com.manajemennilai.dto.response.ProjectResponse;
import com.manajemennilai.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller untuk manajemen proyek.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'LECTURER')")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @RequestBody CreateProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/evaluate")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<String> evaluateProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.evaluateProject(id));
    }
}
