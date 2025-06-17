// src/main/java/com/manajemennilai/service/TaskService.java
package com.manajemennilai.service;

import com.manajemennilai.dto.request.CreateTaskRequest;
import com.manajemennilai.dto.response.TaskResponse;
import com.manajemennilai.exception.errors.ResourceNotFoundException;
import com.manajemennilai.model.*;
import com.manajemennilai.payload.UpdateTaskStatusRequest;
import com.manajemennilai.repository.ProjectRepository;
import com.manajemennilai.repository.TaskRepository;
import com.manajemennilai.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskResponse createTask(CreateTaskRequest request) {
        logger.info("Creating task for project ID: {}", request.getProjectId());

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + request.getProjectId()));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setMilestone(request.isMilestone());
        task.setProject(project);

        if (request.getAssignedToId() != null) {
            User assignedTo = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getAssignedToId()));
            task.setAssignedTo(assignedTo);
        }

        taskRepository.save(task);
        return mapToResponse(task);
    }

    public List<TaskResponse> getTasksByProject(Long projectId) {
        logger.debug("Fetching tasks for project ID: {}", projectId);
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }
        return tasks.stream()
                .map(this::mapToResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTaskStatus(Long id, UpdateTaskStatusRequest request) {
        logger.info("Updating status for task ID: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        try {
            TaskStatus status = TaskStatus.valueOf(request.getStatus().toUpperCase());
            task.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + request.getStatus());
        }

        taskRepository.save(task);
        return mapToResponse(task);
    }

    public void deleteTask(Long id) {
        logger.info("Deleting task ID: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        taskRepository.delete(task);
    }

    private TaskResponse mapToResponse(Task task) {
        try {
            TaskResponse response = new TaskResponse();
            response.setId(task.getId());
            response.setTitle(task.getTitle());
            response.setDescription(task.getDescription());
            response.setDeadline(task.getDeadline());

            if (task.getStatus() != null) {
                response.setStatus(task.getStatus().name());
            } else {
                response.setStatus(TaskStatus.NOT_STARTED.name());
            }

            response.setMilestone(task.isMilestone());

            if (task.getProject() != null) {
                response.setProjectId(task.getProject().getId());
            }

            if (task.getAssignedTo() != null) {
                response.setAssignedToId(task.getAssignedTo().getId());
            }
            return response;
        } catch (Exception e) {
            logger.error("Gagal memetakan Task dengan ID: {}. Error: {}", task.getId(), e.getMessage());
            return null;
        }
    }
}
