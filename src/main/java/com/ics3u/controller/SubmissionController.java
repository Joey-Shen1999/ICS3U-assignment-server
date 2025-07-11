package com.ics3u.controller;

import com.ics3u.entity.Assignment;
import com.ics3u.entity.Student;
import com.ics3u.entity.Submission;
import com.ics3u.repository.AssignmentRepository;
import com.ics3u.repository.StudentRepository;
import com.ics3u.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    // 新建提交（直接存 Submission，不含文件上传）
    @PostMapping
    public Submission createSubmission(@RequestBody Submission submission) {
        return submissionRepository.save(submission);
    }

    // 获取所有提交
    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    // 通过 id 获取单个提交
    @GetMapping("/{id}")
    public Submission getSubmissionById(@PathVariable Long id) {
        return submissionRepository.findById(id).orElse(null);
    }

    // 获取某个 assignment 下的所有提交
    @GetMapping("/assignment/{assignmentId}")
    public List<Submission> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }

    // 获取某个学生的所有提交
    @GetMapping("/student/{ownerId}")
    public List<Submission> getSubmissionsByStudent(@PathVariable Long ownerId) {
        return submissionRepository.findByOwnerId(ownerId);
    }

    // 更新提交
    @PutMapping("/{id}")
    public Submission updateSubmission(@PathVariable Long id, @RequestBody Submission submission) {
        submission.setId(id);
        return submissionRepository.save(submission);
    }

    // 删除提交
    @DeleteMapping("/{id}")
    public void deleteSubmission(@PathVariable Long id) {
        submissionRepository.deleteById(id);
    }

    /**
     * 文件上传 + 创建 Submission
     * folder参数可选，默认submissions，传downloads时会存到downloads目录
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadSubmission(
            @RequestParam("file") MultipartFile file,
            @RequestParam("assignmentId") Long assignmentId,
            @RequestParam("studentId") Long studentId,
            @RequestParam(value = "folder", required = false, defaultValue = "submissions") String folder
    ) {

        try {
            // 1. 查找 assignment 和 student
            Optional<Assignment> assignmentOpt = assignmentRepository.findById(assignmentId);
            Optional<Student> studentOpt = studentRepository.findById(studentId);
            if (!assignmentOpt.isPresent() || !studentOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Assignment or Student not found");
            }

            // 2. 保存文件到指定文件夹（/srv/ics3u-uploads/{folder}/）
            String uploadDir = "/srv/ics3u-uploads/" + folder + "/";
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            dest.getParentFile().mkdirs();
            System.out.println("上传目标路径：" + dest.getAbsolutePath());
            file.transferTo(dest);

            // 3. 保存 Submission 记录
            Submission sub = new Submission();
            sub.setAssignment(assignmentOpt.get());
            sub.setOwner(studentOpt.get());
            sub.setGrade(0.0);
            sub.setSubmittedAt(LocalDateTime.now());
            // 数据库存相对路径
            sub.setFilePath("/uploads/" + folder + "/" + filename);

            Submission saved = submissionRepository.save(sub);

            return ResponseEntity.ok(saved);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("文件上传失败");
        }
    }
}
