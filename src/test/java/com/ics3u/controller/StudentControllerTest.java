package com.ics3u.controller;

import com.ics3u.dto.LoginResponse;
import com.ics3u.entity.Student;
import com.ics3u.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 mock
    }

    @Test
    void testRegister_Success() {
        Student student = new Student("joey", "pw", "Joey Shen", "joey@example.com");
        when(studentRepository.findByUsername("joey")).thenReturn(Optional.empty());
        when(studentRepository.findByEmail("joey@example.com")).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ResponseEntity<?> response = studentController.register(student);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Student);

        Student saved = (Student) response.getBody();
        assertEquals("joey", saved.getUsername());

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testRegister_DuplicateUsername() {
        Student student = new Student("joey", "pw", "Joey Shen", "joey@example.com");
        when(studentRepository.findByUsername("joey")).thenReturn(Optional.of(student));

        ResponseEntity<?> response = studentController.register(student);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("用户名已存在", response.getBody());
        verify(studentRepository, never()).save(any());
    }

    @Test
    void testRegister_DuplicateEmail() {
        Student student = new Student("joey", "pw", "Joey Shen", "joey@example.com");
        when(studentRepository.findByUsername("joey")).thenReturn(Optional.empty());
        when(studentRepository.findByEmail("joey@example.com")).thenReturn(Optional.of(student));

        ResponseEntity<?> response = studentController.register(student);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("邮箱已注册", response.getBody());
        verify(studentRepository, never()).save(any());
    }

    @Test
    void testLogin() {
        Student student = new Student("joey", "pw", "Joey Shen", "joey@example.com");
        student.setId(1L);

        when(studentRepository.findByUsernameAndPassword("joey", "pw")).thenReturn(Optional.of(student));

        // 调用 controller
        ResponseEntity<?> response = studentController.login(student);

        assertEquals(200, response.getStatusCodeValue());

        Object body = response.getBody();
        assertTrue(body instanceof LoginResponse);
        LoginResponse loginResp = (LoginResponse) body;

        assertEquals(1L, loginResp.getId());
        assertEquals("joey", loginResp.getUsername());
        assertEquals("Joey Shen", loginResp.getFullName());
        assertEquals("joey@example.com", loginResp.getEmail());

        verify(studentRepository, times(1)).findByUsernameAndPassword("joey", "pw");
    }

    @Test
    void testGetAllStudents() {
        List<Student> list = Arrays.asList(
                new Student("a", "1", "A", "a@mail.com"),
                new Student("b", "2", "B", "b@mail.com")
        );
        when(studentRepository.findAll()).thenReturn(list);

        List<Student> students = studentController.getAllStudents();

        assertEquals(2, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById() {
        Student student = new Student("joey", "pw", "Joey Shen", "joey@example.com");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> found = studentController.getStudentById(1L);

        assertTrue(found.isPresent());
        assertEquals("joey", found.get().getUsername());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student("joey", "pw", "Joey Shen", "joey@example.com");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student updated = studentController.updateStudent(1L, student);

        assertEquals("joey", updated.getUsername());
        assertEquals(1L, updated.getId());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentController.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
