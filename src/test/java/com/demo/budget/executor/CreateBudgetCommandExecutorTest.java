package com.demo.budget.executor;
import com.demo.budget.command.CreateBudgetCommand;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateBudgetCommandExecutorTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private UserValidationService userValidationService;

    @InjectMocks
    private CreateBudgetCommandExecutor commandExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute_Success() {
        // Arrange
        CreateBudgetCommand command = new CreateBudgetCommand(1L, Category.ENTERTAINMENT, 500.0);
        Budget savedBudget = new Budget();
        savedBudget.setUserId(command.getUserId());
        savedBudget.setBudgetType(command.getBudgetType());
        savedBudget.setAmount(command.getAmount());
        savedBudget.setSpent(0.0);

        when(userValidationService.isValidUser(command.getUserId())).thenReturn(true);
        when(budgetRepository.findByUserIdAndBudgetType(command.getUserId(), command.getBudgetType()))
                .thenReturn(Optional.empty());
        when(budgetRepository.save(any(Budget.class))).thenReturn(savedBudget);

        // Act
        Budget result = commandExecutor.execute(command);

        // Assert
        assertThat(result.getUserId()).isEqualTo(command.getUserId());
        assertThat(result.getBudgetType()).isEqualTo(command.getBudgetType());
        assertThat(result.getAmount()).isEqualTo(command.getAmount());
        assertThat(result.getSpent()).isEqualTo(0.0);

        verify(userValidationService).isValidUser(command.getUserId());
        verify(budgetRepository).findByUserIdAndBudgetType(command.getUserId(), command.getBudgetType());
        verify(budgetRepository).save(any(Budget.class));
    }

    @Test
    public void testExecute_InvalidUser() {
        // Arrange
        CreateBudgetCommand command = new CreateBudgetCommand(1L, Category.ENTERTAINMENT, 500.0);

        when(userValidationService.isValidUser(command.getUserId())).thenReturn(false);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commandExecutor.execute(command);
        });
        assertThat(thrown.getMessage()).isEqualTo("Invalid userId: " + command.getUserId());

        verify(userValidationService).isValidUser(command.getUserId());
        verify(budgetRepository, never()).findByUserIdAndBudgetType(anyLong(), any());
        verify(budgetRepository, never()).save(any(Budget.class));
    }

    @Test
    public void testExecute_BudgetTypeAlreadyExists() {
        // Arrange
        CreateBudgetCommand command = new CreateBudgetCommand(1L, Category.ENTERTAINMENT, 500.0);

        when(userValidationService.isValidUser(command.getUserId())).thenReturn(true);
        when(budgetRepository.findByUserIdAndBudgetType(command.getUserId(), command.getBudgetType()))
                .thenReturn(Optional.of(new Budget()));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commandExecutor.execute(command);
        });
        assertThat(thrown.getMessage()).isEqualTo("Budget Type and User Id configuration exists : " + command.getUserId());

        verify(userValidationService).isValidUser(command.getUserId());
        verify(budgetRepository).findByUserIdAndBudgetType(command.getUserId(), command.getBudgetType());
        verify(budgetRepository, never()).save(any(Budget.class));
    }
}
