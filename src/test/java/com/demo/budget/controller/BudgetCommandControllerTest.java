package com.demo.budget.controller;
import com.demo.budget.command.CreateBudgetCommand;
import com.demo.budget.command.DeleteBudgetCommand;
import com.demo.budget.command.UpdateBudgetCommand;
import com.demo.budget.model.Budget;
import com.demo.budget.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class BudgetCommandControllerTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetCommandController budgetCommandController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBudget() {
        // Arrange
        Budget budget = new Budget();
        when(budgetService.executeCommand(any(CreateBudgetCommand.class))).thenReturn(budget);

        // Act
        ResponseEntity<Budget> response = budgetCommandController.createBudget(new CreateBudgetCommand());

        // Assert
        assertEquals(budget, response.getBody());
    }

    @Test
    void testUpdateBudget() {
        // Arrange
        Budget budget = new Budget();
        when(budgetService.executeCommand(any(UpdateBudgetCommand.class))).thenReturn(budget);

        // Act
        ResponseEntity<Budget> response = budgetCommandController.updateBudget(1L, new UpdateBudgetCommand());

        // Assert
        assertEquals(budget, response.getBody());
    }
    @Test
    void testDeleteBudget() {
        // Arrange
        when(budgetService.executeCommand(any(DeleteBudgetCommand.class))).thenReturn(null);

        // Act
        ResponseEntity<Void> response = budgetCommandController.deleteBudget(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());  // 204 No Content
    }
}
