package com.demo.budget.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserValidationService {

    private final RestTemplate restTemplate;

    @Autowired
    public UserValidationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValidUser(Long userId) {
        try {
            // Assuming the User Service has an endpoint like /api/users/{userId}
            String url = "http://localhost:8080/users/api/query/" + userId;  // Replace with actual User Service URL
            restTemplate.getForObject(url, String.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            // User not found in the User Service
            return false;
        } catch (Exception e) {
            // Handle other exceptions such as connectivity issues
            throw new RuntimeException("User not found " + userId, e);
        }
    }
    public String getUserEmail(Long userId) {
        try {
            // The endpoint provided by the User Service to fetch the email
            String url = "http://localhost:8080/users/api/query/" + userId + "/email";  // Replace with actual User Service URL
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException.NotFound e) {
            // Handle the case where the user is not found
            throw new IllegalArgumentException("User not found with userId: " + userId);
        } catch (Exception e) {
            // Handle other exceptions, such as connectivity issues
            throw new RuntimeException("Failed fetching email for userId: " + userId, e);
        }
    }
}
