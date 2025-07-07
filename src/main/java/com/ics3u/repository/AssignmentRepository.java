package com.ics3u.repository;

import com.ics3u.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    // 例如：查找某个学生的所有作业
    List<Assignment> findByOwnerId(Long ownerId);
    List<Assignment> findByAssignmentNumber(Integer assignmentNumber);

}
