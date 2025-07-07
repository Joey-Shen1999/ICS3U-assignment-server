package com.ics3u.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testStudentConstructorAndGetters() {
        Student student = new Student("joey", "123456", "Joey Shen", "joey@example.com");

        assertEquals("joey", student.getUsername());
        assertEquals("123456", student.getPassword());
        assertEquals("Joey Shen", student.getFullName());
        assertEquals("joey@example.com", student.getEmail());
        assertNull(student.getId());
    }

    @Test
    void testSetters() {
        Student student = new Student();
        student.setUsername("amy");
        student.setPassword("abcdef");
        student.setFullName("Amy Smith");
        student.setEmail("amy@example.com");

        assertEquals("amy", student.getUsername());
        assertEquals("abcdef", student.getPassword());
        assertEquals("Amy Smith", student.getFullName());
        assertEquals("amy@example.com", student.getEmail());
    }
}
