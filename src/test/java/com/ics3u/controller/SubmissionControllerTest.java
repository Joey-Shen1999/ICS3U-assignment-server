package com.ics3u.controller;

import com.ics3u.entity.Submission;
import com.ics3u.repository.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SubmissionControllerTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private SubmissionController submissionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSubmissions() {
        List<Submission> list = Arrays.asList(new Submission(), new Submission());
        when(submissionRepository.findAll()).thenReturn(list);

        List<Submission> result = submissionController.getAllSubmissions();

        assertEquals(2, result.size());
        verify(submissionRepository, times(1)).findAll();
    }

    @Test
    void testGetSubmissionById() {
        Submission submission = new Submission();
        submission.setId(1L);
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));

        Submission found = submissionController.getSubmissionById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        verify(submissionRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateSubmission() {
        Submission submission = new Submission();
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);

        Submission saved = submissionController.createSubmission(submission);

        assertNotNull(saved);
        verify(submissionRepository, times(1)).save(submission);
    }

    @Test
    void testUpdateSubmission() {
        Submission submission = new Submission();
        submission.setId(1L);
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);

        Submission updated = submissionController.updateSubmission(1L, submission);

        assertEquals(1L, updated.getId());
        verify(submissionRepository, times(1)).save(submission);
    }

    @Test
    void testDeleteSubmission() {
        doNothing().when(submissionRepository).deleteById(1L);

        submissionController.deleteSubmission(1L);

        verify(submissionRepository, times(1)).deleteById(1L);
    }
}
