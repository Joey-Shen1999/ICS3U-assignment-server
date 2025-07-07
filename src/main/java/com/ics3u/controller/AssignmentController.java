package com.ics3u.controller;

import com.ics3u.entity.Assignment;
import com.ics3u.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    // 新建
    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    // 查所有
    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // 查单个
    @GetMapping("/{id}")
    public Optional<Assignment> getAssignmentById(@PathVariable Long id) {
        return assignmentRepository.findById(id);
    }

    // 更新
    @PutMapping("/{id}")
    public Assignment updateAssignment(@PathVariable Long id, @RequestBody Assignment assignment) {
        assignment.setId(id);
        return assignmentRepository.save(assignment);
    }

    // 删除
    @DeleteMapping("/{id}")
    public void deleteAssignment(@PathVariable Long id) {
        assignmentRepository.deleteById(id);
    }

    // 查某个 ownerId 的所有 assignment
    @GetMapping("/owner/{ownerId}")
    public List<Assignment> getAssignmentsByOwner(@PathVariable Long ownerId) {
        return assignmentRepository.findByOwnerId(ownerId);
    }

}
