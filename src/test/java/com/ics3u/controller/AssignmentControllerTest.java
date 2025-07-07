package com.ics3u.controller;

import com.ics3u.entity.Assignment;
import com.ics3u.entity.Student;
import com.ics3u.repository.AssignmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssignmentControllerTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentController assignmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAssignment() {
        Assignment assignment = new Assignment(1, "Title", "Desc", LocalDate.now(), new Student());
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        Assignment saved = assignmentController.createAssignment(assignment);

        assertEquals("Title", saved.getTitle());
        verify(assignmentRepository, times(1)).save(assignment);
    }

    @Test
    void testGetAllAssignments() {
        List<Assignment> list = Arrays.asList(
                new Assignment(1, "A", "d1", LocalDate.now(), new Student()),
                new Assignment(2, "B", "d2", LocalDate.now(), new Student())
        );
        when(assignmentRepository.findAll()).thenReturn(list);

        List<Assignment> assignments = assignmentController.getAllAssignments();

        assertEquals(2, assignments.size());
        verify(assignmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAssignmentById() {
        Assignment assignment = new Assignment(1, "Title", "Desc", LocalDate.now(), new Student());
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));

        Optional<Assignment> found = assignmentController.getAssignmentById(1L);

        assertTrue(found.isPresent());
        assertEquals("Title", found.get().getTitle());
        verify(assignmentRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateAssignment() {
        Assignment assignment = new Assignment(1, "Title", "Desc", LocalDate.now(), new Student());
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        Assignment updated = assignmentController.updateAssignment(1L, assignment);

        assertEquals(1L, updated.getId());
        verify(assignmentRepository, times(1)).save(assignment);
    }

    @Test
    void testDeleteAssignment() {
        doNothing().when(assignmentRepository).deleteById(1L);

        assignmentController.deleteAssignment(1L);

        verify(assignmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAssignmentsByOwnerId() {
        // 新增：测试查 ownerId 拥有的 assignments
        Student admin = new Student("admin", "Admin", "管理员", "admin@example.com");
        admin.setId(1L);

        List<Assignment> list = Arrays.asList(
                new Assignment(1, "A", "d1", LocalDate.now(), admin),
                new Assignment(2, "B", "d2", LocalDate.now(), admin)
        );
        when(assignmentRepository.findByOwnerId(1L)).thenReturn(list);

        List<Assignment> assignments = assignmentController.getAssignmentsByOwner(1L);

        assertEquals(2, assignments.size());
        assertEquals("A", assignments.get(0).getTitle());
        assertEquals("B", assignments.get(1).getTitle());
        verify(assignmentRepository, times(1)).findByOwnerId(1L);
    }
}
