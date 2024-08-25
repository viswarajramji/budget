package com.demo.budget.executor;
import com.demo.budget.command.DeleteBudgetCommand;
import com.demo.budget.repo.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class DeleteBudgetCommandExecutorTest {

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private DeleteBudgetCommandExecutor commandExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        // Arrange
        Long budgetId = 1L;
        DeleteBudgetCommand command = new DeleteBudgetCommand(budgetId);

        // Act
        commandExecutor.execute(command);

        // Assert
        verify(budgetRepository, times(1)).deleteById(budgetId);
    }
}
