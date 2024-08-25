package com.demo.budget.service;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserValidationServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private UserValidationService userValidationService;

    @Test
    public void testIsValidUser_validUser() {
        // Arrange
        Long userId = 1L;
        when(restTemplate.getForObject(anyString(), Mockito.any()))
                .thenReturn("User found");

        // Act
        boolean result = userValidationService.isValidUser(userId);

        // Assert
        assert(result);
    }

    @Test
    public void testIsValidUser_notFound() {
        // Arrange
        Long userId = 1L;
        when(restTemplate.getForObject(anyString(), Mockito.any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userValidationService.isValidUser(userId);
        });
        assertEquals("User not found " + userId, thrown.getMessage());
    }

    @Test
    public void testIsValidUser_exception() {
        // Arrange
        Long userId = 1L;
        when(restTemplate.getForObject(anyString(), Mockito.any()))
                .thenThrow(new RuntimeException("Connection error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userValidationService.isValidUser(userId);
        });
        assertEquals("User not found " + userId, thrown.getMessage());
    }

    @Test
    public void testGetUserEmail_validUser() {
        // Arrange
        Long userId = 1L;
        String email = "user@example.com";
        when(restTemplate.getForObject(anyString(), Mockito.any()))
                .thenReturn(email);

        // Act
        String result = userValidationService.getUserEmail(userId);

        // Assert
        assertEquals(email, result);
    }

    @Test
    public void testGetUserEmail_notFound() {
        // Arrange
        Long userId = 1L;
        when(restTemplate.getForObject(anyString(), Mockito.any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userValidationService.getUserEmail(userId);
        });
        assertEquals("Failed fetching email for userId: " + userId, thrown.getMessage());
    }

    @Test
    public void testGetUserEmail_exception() {
        // Arrange
        Long userId = 1L;
        when(restTemplate.getForObject(anyString(), Mockito.any()))
                .thenThrow(new RuntimeException("Connection error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userValidationService.getUserEmail(userId);
        });
        assertEquals("Failed fetching email for userId: " + userId, thrown.getMessage());
    }
}
