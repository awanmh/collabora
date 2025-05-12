// ProjectService.java

package com.manajemennilai.service;

import com.manajemennilai.dto.request.CreateProjectRequest;
import com.manajemennilai.dto.response.ProjectResponse;
import com.manajemennilai.exception.errors.ResourceNotFoundException;
import com.manajemennilai.model.Project;
import com.manajemennilai.model.User;
import com.manajemennilai.repository.ProjectRepository;
import com.manajemennilai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service untuk manajemen proyek.
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectResponse createProject(CreateProjectRequest request) {
        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());

        List<User> members = request.getMemberIds().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id)))
                .collect(Collectors.toList());
        project.setMembers(members);

        projectRepository.save(project);
        return mapToResponse(project);
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
        return mapToResponse(project);
    }

    public ProjectResponse updateProject(Long id, CreateProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());

        List<User> members = request.getMemberIds().stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId)))
                .collect(Collectors.toList());
        project.setMembers(members);

        projectRepository.save(project);
        return mapToResponse(project);
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
        projectRepository.delete(project);
    }

    public String evaluateProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
        // Logika evaluasi dosen (contoh sederhana)
        return "Evaluation for project " + project.getTitle() + ": All tasks completed.";
    }

    private ProjectResponse mapToResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setTitle(project.getTitle());
        response.setDescription(project.getDescription());
        response.setMemberIds(project.getMembers().stream()
                .map(User::getId)
                .collect(Collectors.toList()));
        return response;
    }
}