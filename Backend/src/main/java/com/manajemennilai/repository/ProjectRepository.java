// ProjectRepository.java

package com.manajemennilai.repository;

import com.manajemennilai.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository untuk entitas Project.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
}