// CreateTaskRequest.java

<<<<<<< HEAD
package com.manajemennilai.payload;
=======
package com.manajemennilai.payload.request;
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTaskRequest {

    @NotBlank(message = "Task title is required")
    private String title;

    @NotBlank(message = "Task description is required")
    private String description;

    @NotNull(message = "Due date is required")
    private Long dueDate; // Representing timestamp for due date
}