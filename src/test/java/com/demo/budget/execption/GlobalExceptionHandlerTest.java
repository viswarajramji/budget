package com.demo.budget.execption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleValidationExceptions() {
        // Arrange
        FieldError fieldError = new FieldError("objectName", "fieldName", "DefaultMessage");
        BindException bindException = new BindException(new Object(), "objectName");
        bindException.addError(fieldError);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindException);

        // Act
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(ex);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals("DefaultMessage", response.getBody().get("fieldName"));
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testHandleDataIntegrityViolationException() {
        // Arrange
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        when(ex.getMessage()).thenReturn("Data Integrity Violation");

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleDataIntegrityViolationException(ex);

        // Assert
        assertNotNull(response);
        assertEquals("Data Integrity Violation", response.getBody());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testHandleRunTimeException() {
        // Arrange
        RuntimeException ex = mock(RuntimeException.class);
        when(ex.getMessage()).thenReturn("Runtime Exception");

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleRunTimeException(ex);

        // Assert
        assertNotNull(response);
        assertEquals("Runtime Exception", response.getBody());
        assertEquals(400, response.getStatusCodeValue());
    }
}