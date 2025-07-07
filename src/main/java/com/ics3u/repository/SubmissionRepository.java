package com.ics3u.repository;

import com.ics3u.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    // 查找某个作业下所有提交
    List<Submission> findByAssignmentId(Long assignmentId);

    // 查找某个学生的所有提交
    List<Submission> findByOwnerId(Long ownerId);

    // 查找某个学生对某个作业的所有提交
    List<Submission> findByAssignmentIdAndOwnerId(Long assignmentId, Long ownerId);
}
