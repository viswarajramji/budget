package com.demo.budget.executor;
import com.demo.budget.command.UpdateBudgetCommand;
import com.demo.budget.enums.Category;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import com.demo.budget.service.UserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UpdateBudgetCommandExecutorTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private UserValidationService userValidationService;

    @InjectMocks
    private UpdateBudgetCommandExecutor commandExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute_UserNotValid_ThrowsException() {
        // Arrange
        Long userId = 1L;
        UpdateBudgetCommand command = new UpdateBudgetCommand();
        command.setUserId(userId);

        when(userValidationService.isValidUser(userId)).thenReturn(false);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                commandExecutor.execute(command)
        );
        assertEquals("Invalid userId: " + userId, thrown.getMessage());
    }

    @Test
    public void testExecute_BudgetTypeAndUserIdConfigurationExists_ThrowsException() {
        // Arrange
        Long userId = 1L;
        String budgetType = "ENTERTAINMENT";
        UpdateBudgetCommand command = new UpdateBudgetCommand();
        command.setUserId(userId);
        command.setBudgetType(Category.ENTERTAINMENT);

        when(userValidationService.isValidUser(userId)).thenReturn(true);
        when(budgetRepository.findByUserIdAndBudgetType(userId, Category.ENTERTAINMENT)).thenReturn(Optional.of(new Budget()));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                commandExecutor.execute(command)
        );
        assertEquals("Budget Type and User Id configuration exists for userId : " + userId, thrown.getMessage());
    }

    @Test
    public void testExecute_BudgetNotFound_ThrowsException() {
        // Arrange
        Long budgetId = 1L;
        Long userId = 1L;
        String budgetType = "ENTERTAINMENT";
        UpdateBudgetCommand command = new UpdateBudgetCommand();
        command.setBudgetId(budgetId);
        command.setUserId(userId);
        command.setBudgetType(Category.ENTERTAINMENT);
        command.setAmount(500.0);

        when(userValidationService.isValidUser(userId)).thenReturn(true);
        when(budgetRepository.findByUserIdAndBudgetType(userId, Category.ENTERTAINMENT)).thenReturn(Optional.empty());
        when(budgetRepository.findById(budgetId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                commandExecutor.execute(command)
        );
        assertEquals("Budget not found", thrown.getMessage());
    }

    @Test
    public void testExecute_Success() {
        // Arrange
        Long budgetId = 1L;
        Long userId = 1L;
        String budgetType = "ENTERTAINMENT";
        Double newAmount = 500.0;
        Budget existingBudget = new Budget();
        existingBudget.setId(budgetId);
        existingBudget.setUserId(userId);
        existingBudget.setBudgetType(Category.ENTERTAINMENT);
        existingBudget.setAmount(300.0);

        UpdateBudgetCommand command = new UpdateBudgetCommand();
        command.setBudgetId(budgetId);
        command.setUserId(userId);
        command.setBudgetType(Category.ENTERTAINMENT);
        command.setAmount(newAmount);

        when(userValidationService.isValidUser(userId)).thenReturn(true);
        when(budgetRepository.findByUserIdAndBudgetType(userId, Category.ENTERTAINMENT)).thenReturn(Optional.empty());
        when(budgetRepository.findById(budgetId)).thenReturn(Optional.of(existingBudget));
        when(budgetRepository.save(existingBudget)).thenReturn(existingBudget);

        // Act
        Budget result = commandExecutor.execute(command);

        // Assert
        assertEquals(budgetType, result.getBudgetType().name());
        assertEquals(newAmount, result.getAmount());
    }
}
