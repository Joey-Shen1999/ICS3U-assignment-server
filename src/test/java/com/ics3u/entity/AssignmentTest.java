package com.ics3u.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

    @Test
    void testAssignmentGettersAndSetters() {
        // 准备一个学生
        Student student = new Student();
        student.setId(123L);
        student.setUsername("joey");
        student.setPassword("password");

        // 构造 Assignment
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setAssignmentNumber(2);
        assignment.setTitle("Test Title");
        assignment.setDescription("Test Desc");
        assignment.setDueDate(LocalDate.of(2025, 7, 1));
        assignment.setOwner(student);

        // 验证 Assignment 字段
        assertEquals(1L, assignment.getId());
        assertEquals(2, assignment.getAssignmentNumber());
        assertEquals("Test Title", assignment.getTitle());
        assertEquals("Test Desc", assignment.getDescription());
        assertEquals(LocalDate.of(2025, 7, 1), assignment.getDueDate());

        // 重点：获取 owner id，必须这样写
        assertNotNull(assignment.getOwner());
        assertEquals(123L, assignment.getOwner().getId());
    }
}
