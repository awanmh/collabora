//  CreateCommentRequest.java (DTO untuk pembuatan komentar)

package com.manajemennilai.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO untuk request pembuatan komentar.
 */
public class CreateCommentRequest {

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Task ID is required")
    private Long taskId;

    private Long parentCommentId; // Untuk threaded comments

    // Getters and setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}