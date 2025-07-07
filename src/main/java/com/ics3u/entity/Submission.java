package com.ics3u.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Student owner;

    private Double grade;

    private LocalDateTime submittedAt;

    // 新增：存文件路径
    @Column(name = "file_path")
    private String filePath;

    // Constructors
    public Submission() {}

    public Submission(Assignment assignment, Student owner, Double grade, LocalDateTime submittedAt, String filePath) {
        this.assignment = assignment;
        this.owner = owner;
        this.grade = grade;
        this.submittedAt = submittedAt;
        this.filePath = filePath;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Assignment getAssignment() { return assignment; }
    public void setAssignment(Assignment assignment) { this.assignment = assignment; }

    public Student getOwner() { return owner; }
    public void setOwner(Student owner) { this.owner = owner; }

    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}
