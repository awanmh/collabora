// src/main/java/com/manajemennilai/controller/MemberController.java
package com.manajemennilai.controller;

import com.manajemennilai.dto.response.MemberResponse;
import com.manajemennilai.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService; // Gunakan Service, bukan Repository langsung

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        List<MemberResponse> students = memberService.getAllStudents();
        // Bungkus list dalam Map agar menjadi JSON object { "students": [...] }
        Map<String, List<MemberResponse>> response = new HashMap<>();
        response.put("students", students);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lecturers")
    public ResponseEntity<?> getAllLecturers() {
        List<MemberResponse> lecturers = memberService.getAllLecturers();
        // Bungkus list dalam Map agar menjadi JSON object { "lecturers": [...] }
        Map<String, List<MemberResponse>> response = new HashMap<>();
        response.put("lecturers", lecturers);
        return ResponseEntity.ok(response);
    }
}
