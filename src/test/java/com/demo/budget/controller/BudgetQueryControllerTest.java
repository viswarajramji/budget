package com.demo.budget.controller;
import com.demo.budget.model.Budget;
import com.demo.budget.query.GetAllBudgetsQuery;
import com.demo.budget.query.GetBudgetByUserIdQuery;
import com.demo.budget.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BudgetQueryControllerTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetQueryController budgetQueryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBudgetById() {
        // Arrange
        Long userId = 1L;
        List<Budget> budgets = Arrays.asList(new Budget(), new Budget());
        when(budgetService.executeQuery(any(GetBudgetByUserIdQuery.class))).thenReturn(budgets);

        // Act
        ResponseEntity<List<Budget>> response = budgetQueryController.getBudgetById(userId);

        // Assert
        assertEquals(budgets, response.getBody());
        assertEquals(200, response.getStatusCodeValue());  // HTTP 200 OK
    }

    @Test
    void testGetAllBudgets() {
        // Arrange
        List<Budget> budgets = Arrays.asList(new Budget(), new Budget());
        when(budgetService.executeQuery(any(GetAllBudgetsQuery.class))).thenReturn(budgets);

        // Act
        ResponseEntity<List<Budget>> response = budgetQueryController.getAllBudgets();

        // Assert
        assertEquals(budgets, response.getBody());
        assertEquals(200, response.getStatusCodeValue());  // HTTP 200 OK
    }
}
