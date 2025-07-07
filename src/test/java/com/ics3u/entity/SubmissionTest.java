package com.ics3u.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubmissionTest {

    @Test
    void testSubmissionConstructorAndGetters() {
        Student owner = new Student("joey", "123456", "Joey Shen", "joey@example.com");
        Assignment assignment = new Assignment(1, "Title", "Desc", LocalDate.now(), owner);
        LocalDateTime submitTime = LocalDateTime.of(2025, 7, 1, 20, 0, 0);

        // 现在构造器没有 filePath 参数，直接 set
        Submission submission = new Submission(assignment, owner, 95.0, submitTime, "uploads/submissions/test1.pdf");
        submission.setFilePath("uploads/submissions/test1.pdf");

        assertNull(submission.getId());
        assertEquals(assignment, submission.getAssignment());
        assertEquals(owner, submission.getOwner());
        assertEquals(95.0, submission.getGrade());
        assertEquals(submitTime, submission.getSubmittedAt());
        assertEquals("uploads/submissions/test1.pdf", submission.getFilePath());
    }

    @Test
    void testSetters() {
        Submission submission = new Submission();
        Student owner = new Student("amy", "abcdef", "Amy Smith", "amy@example.com");
        Assignment assignment = new Assignment(2, "Java OOP", "Do it", LocalDate.now(), owner);
        LocalDateTime submitTime = LocalDateTime.now();

        submission.setAssignment(assignment);
        submission.setOwner(owner);
        submission.setGrade(100.0);
        submission.setSubmittedAt(submitTime);
        submission.setFilePath("uploads/submissions/amy_java.pdf");

        assertEquals(assignment, submission.getAssignment());
        assertEquals(owner, submission.getOwner());
        assertEquals(100.0, submission.getGrade());
        assertEquals(submitTime, submission.getSubmittedAt());
        assertEquals("uploads/submissions/amy_java.pdf", submission.getFilePath());
    }
}
