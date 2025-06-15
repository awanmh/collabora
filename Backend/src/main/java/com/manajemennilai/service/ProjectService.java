// src/main/java/com/manajemennilai/service/ProjectService.java
package com.manajemennilai.service;

import com.manajemennilai.dto.request.CreateProjectRequest;
import com.manajemennilai.dto.response.MemberResponse;
import com.manajemennilai.dto.response.ProjectResponse;
import com.manajemennilai.exception.errors.ResourceNotFoundException;
import com.manajemennilai.model.Project;
import com.manajemennilai.model.User;
import com.manajemennilai.repository.ProjectRepository;
import com.manajemennilai.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository; // Diperlukan untuk menambah/mengupdate anggota

    /**
     * Mengambil semua proyek yang ada.
     */
    public List<ProjectResponse> getAllProjects() {
        logger.debug("Mengambil semua proyek");
        return projectRepository.findAll().stream()
                .map(this::mapToProjectResponse)
                .collect(Collectors.toList());
    }

    /**
     * Mengambil satu proyek berdasarkan ID dan memetakannya ke DTO.
     */
    public ProjectResponse getProjectById(Long projectId) {
        logger.debug("Mencari proyek dengan ID: {}", projectId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyek dengan ID " + projectId + " tidak ditemukan."));

        return mapToProjectResponse(project);
    }

    /**
     * Membuat proyek baru berdasarkan data dari request.
     */
    @Transactional
    public ProjectResponse createProject(CreateProjectRequest request) {
        logger.info("Membuat proyek baru dengan judul: {}", request.getTitle());

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());

        // Menggunakan daftar memberIds dari request untuk menambahkan anggota
        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            List<User> members = userRepository.findAllById(request.getMemberIds());
            if(members.size() != request.getMemberIds().size()){
                throw new ResourceNotFoundException("Satu atau lebih ID anggota tidak ditemukan.");
            }
            project.setMembers(members);
        }

        Project savedProject = projectRepository.save(project);
        logger.info("Proyek berhasil disimpan dengan ID: {}", savedProject.getId());

        return mapToProjectResponse(savedProject);
    }

    /**
     * Mengupdate proyek yang sudah ada.
     */
    @Transactional
    public ProjectResponse updateProject(Long id, CreateProjectRequest request) {
        logger.info("Mengupdate proyek dengan ID: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyek dengan ID " + id + " tidak ditemukan."));

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());

        // Mengupdate daftar anggota
        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            List<User> members = userRepository.findAllById(request.getMemberIds());
            if(members.size() != request.getMemberIds().size()){
                throw new ResourceNotFoundException("Satu atau lebih ID anggota tidak ditemukan saat update.");
            }
            project.setMembers(members);
        } else {
            project.getMembers().clear(); // Hapus semua anggota jika list kosong
        }

        Project updatedProject = projectRepository.save(project);
        return mapToProjectResponse(updatedProject);
    }

    /**
     * Menghapus proyek berdasarkan ID.
     */
    public void deleteProject(Long id) {
        logger.info("Menghapus proyek dengan ID: {}", id);
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proyek dengan ID " + id + " tidak ditemukan.");
        }
        projectRepository.deleteById(id);
    }

    /**
     * Placeholder untuk logika evaluasi proyek oleh dosen.
     */
    public String evaluateProject(Long id) {
        logger.info("Mengevaluasi proyek dengan ID: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyek dengan ID " + id + " tidak ditemukan."));
        // Logika evaluasi bisa ditambahkan di sini
        return "Proyek '" + project.getTitle() + "' telah dievaluasi.";
    }


    /**
     * Mapper yang aman untuk mengubah Project Entity menjadi ProjectResponse DTO.
     */
    private ProjectResponse mapToProjectResponse(Project project) {
        try {
            ProjectResponse response = new ProjectResponse();
            response.setId(project.getId());
            response.setTitle(project.getTitle());
            response.setDescription(project.getDescription());

            // Memetakan daftar anggota dengan aman
            if (project.getMembers() != null) {
                List<MemberResponse> memberResponses = project.getMembers().stream().map(user -> {
                    if (user == null) return null;

                    MemberResponse memberDto = new MemberResponse();
                    memberDto.setId(user.getId());

                    MemberResponse.UserDetails userDetails = new MemberResponse.UserDetails(user.getUsername());
                    memberDto.setUser(userDetails);

                    return memberDto;
                }).filter(java.util.Objects::nonNull).collect(Collectors.toList());

                response.setMembers(memberResponses);
            }

            return response;
        } catch (Exception e) {
            logger.error("Gagal memetakan Proyek dengan ID: {}. Error: {}", project.getId(), e.getMessage(), e);
            throw new RuntimeException("Terjadi kesalahan internal saat memproses data proyek.");
        }
    }
}
