// src/main/java/com/manajemennilai/service/MemberService.java
package com.manajemennilai.service;

import com.manajemennilai.dto.response.MemberResponse;
import com.manajemennilai.model.Lecturer;
import com.manajemennilai.model.Student;
import com.manajemennilai.repository.LecturerRepository;
import com.manajemennilai.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    /**
     * Mengambil semua data mahasiswa dan mengubahnya menjadi DTO.
     */
    public List<MemberResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToMemberResponse)
                .collect(Collectors.toList());
    }

    /**
     * Mengambil semua data dosen dan mengubahnya menjadi DTO.
     */
    public List<MemberResponse> getAllLecturers() {
        return lecturerRepository.findAll().stream()
                .map(this::mapToMemberResponse)
                .collect(Collectors.toList());
    }

    // Helper method untuk mengubah Entity Student menjadi DTO
    private MemberResponse mapToMemberResponse(Student student) {
        // DIUBAH DISINI: Menggunakan getUsername() sesuai dengan model User.java
        MemberResponse.UserDetails userDetails = new MemberResponse.UserDetails(student.getUser().getUsername());
        return new MemberResponse(
                student.getId(),
                userDetails,
                student.getStudentId(),
                null // lecturerId tidak relevan untuk mahasiswa
        );
    }

    // Helper method untuk mengubah Entity Lecturer menjadi DTO
    private MemberResponse mapToMemberResponse(Lecturer lecturer) {
        // DIUBAH DISINI: Menggunakan getUsername() sesuai dengan model User.java
        MemberResponse.UserDetails userDetails = new MemberResponse.UserDetails(lecturer.getUser().getUsername());
        return new MemberResponse(
                lecturer.getId(),
                userDetails,
                null, // studentId tidak relevan untuk dosen
                lecturer.getLecturerId()
        );
    }
}
