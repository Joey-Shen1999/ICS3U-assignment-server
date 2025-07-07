package com.ics3u.controller;

import com.ics3u.entity.Student;
import com.ics3u.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ics3u.dto.LoginResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Student student) {
        // 1. 查重
        if (studentRepository.findByUsername(student.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("邮箱已注册");
        }
        // 2. 密码加密（如果上线建议加密存储）
        // student.setPassword(new BCryptPasswordEncoder().encode(student.getPassword()));
        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Student loginStudent) {
        Optional<Student> studentOpt = studentRepository
                .findByUsernameAndPassword(loginStudent.getUsername(), loginStudent.getPassword());
        if (studentOpt.isPresent()) {
            Student s = studentOpt.get();
            LoginResponse resp = new LoginResponse(s.getId(), s.getUsername(), s.getFullName(), s.getEmail());
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.status(401).body("用户名或密码错误");
        }
    }

    // 查询所有学生
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 通过 id 查询学生
    @GetMapping("/{id:\\d+}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id);
    }

    // 修改学生信息
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    // 删除学生
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
