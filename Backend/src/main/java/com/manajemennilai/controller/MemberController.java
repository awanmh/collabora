package com.manajemennilai.controller;

import com.manajemennilai.model.Student;
import com.manajemennilai.model.Lecturer;
import com.manajemennilai.repository.StudentRepository;
import com.manajemennilai.repository.LecturerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/lecturers")
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }
}
