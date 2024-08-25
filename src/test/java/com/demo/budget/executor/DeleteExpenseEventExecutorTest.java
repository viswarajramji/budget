package com.demo.budget.executor;
import com.demo.budget.command.UpdateBudgetSpendAmountInternalCommand;
import com.demo.budget.enums.Category;
import com.demo.budget.enums.RecordType;
import com.demo.budget.event.DeleteExpenseEvent;
import com.demo.budget.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class DeleteExpenseEventExecutorTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private DeleteExpenseEventExecutor eventExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        // Arrange
        DeleteExpenseEvent event = new DeleteExpenseEvent(
                1L, 2L, "Expense Name", "Expense Description", Category.EDUCATION, 100.0
        );

        // Act
        eventExecutor.execute(event);

        // Assert
        UpdateBudgetSpendAmountInternalCommand expectedCommand = new UpdateBudgetSpendAmountInternalCommand(
                RecordType.DELETED,
                event.getExpenseId(),
                event.getUserId(),
                event.getExpenseName(),
                event.getExpenseDescription(),
                event.getExpenseType(),
                event.getAmount(),
                -event.getAmount() // Negative amount for deletion
        );

        verify(budgetService).executeCommand(expectedCommand);
    }
}
