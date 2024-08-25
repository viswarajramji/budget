package com.demo.budget.executor;
import com.demo.budget.api.EventExecutor;
import com.demo.budget.command.UpdateBudgetSpendAmountInternalCommand;
import com.demo.budget.enums.Category;
import com.demo.budget.enums.RecordType;
import com.demo.budget.event.UpdateExpenseEvent;
import com.demo.budget.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class UpdateExpenseEventExecutorTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private UpdateExpenseEventExecutor eventExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        // Arrange
        Long expenseId = 1L;
        Long userId = 2L;
        String expenseName = "Books";
        String expenseDescription = "Educational books";
        Category expenseType = Category.EDUCATION;
        Double newAmount = 150.0;
        Double diffAmount = 50.0;

        UpdateExpenseEvent event = new UpdateExpenseEvent();
        event.setExpenseId(expenseId);
        event.setUserId(userId);
        event.setExpenseName(expenseName);
        event.setExpenseDescription(expenseDescription);
        event.setExpenseType(expenseType);
        event.setNewAmount(newAmount);
        event.setDiffAmount(diffAmount);

        UpdateBudgetSpendAmountInternalCommand expectedCommand = new UpdateBudgetSpendAmountInternalCommand(
                RecordType.MODIFIED,
                expenseId,
                userId,
                expenseName,
                expenseDescription,
                expenseType,
                newAmount,
                diffAmount
        );

        // Act
        eventExecutor.execute(event);

        // Assert
        verify(budgetService, times(1)).executeCommand(expectedCommand);
    }
}
