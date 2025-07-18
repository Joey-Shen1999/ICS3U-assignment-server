package com.ics3u.controller;

import com.ics3u.entity.Assignment;
import com.ics3u.entity.Student;
import com.ics3u.repository.AssignmentRepository;
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
    @Autowired
    private AssignmentRepository assignmentRepository;

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

        // 3. 给新用户创建默认 Assignment
        createDefaultAssignmentForNewUser(saved);
        return ResponseEntity.ok(saved);
    }

    // 新用户注册后自动插入 Assignment 2
    private void createDefaultAssignmentForNewUser(Student saved) {
        Assignment assignment2 = new Assignment();
        assignment2.setAssignmentNumber(2);
        assignment2.setTitle("Assignment 2");
        assignment2.setDescription("本作业主要练习 Java 语言的基本数据类型（int、double、String、char、boolean），掌握基本四则运算与赋值操作，熟悉逻辑运算符的用法，能够进行简单的数据类型转换，并理解表达式的优先级。请根据 PDF 文件完成对应编程题。");
        assignment2.setPdfPath("assignments/assignment2.pdf");
        assignment2.setOwner(saved);
        assignmentRepository.save(assignment2);

        // 新增：自动插入 Assignment 3
        Assignment assignment3 = new Assignment();
        assignment3.setAssignmentNumber(3);
        assignment3.setTitle("Assignment 3");
        assignment3.setDescription("本作业主要练习 Scanner 类用户输入和常见 String 方法的使用，包括格式化输入、字符串分析、截取与替换等。所有输出需保持一致，不使用 if/else 等条件结构。请根据 PDF 文件完成对应练习题。");
        assignment3.setPdfPath("assignments/assignment3.pdf"); // 请确保该路径下有该 PDF 文件
        assignment3.setOwner(saved);
        assignmentRepository.save(assignment3);
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
