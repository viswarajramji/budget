package com.demo.budget.executor;
import com.demo.budget.model.Budget;
import com.demo.budget.query.GetBudgetByUserIdQuery;
import com.demo.budget.repo.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetBudgetByIdCommandExecutorTest {

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private GetBudgetByIdCommandExecutor commandExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        // Arrange
        Long userId = 1L;
        Budget budget1 = new Budget();
        Budget budget2 = new Budget();
        List<Budget> expectedBudgets = Arrays.asList(budget1, budget2);

        when(budgetRepository.findByUserId(userId)).thenReturn(expectedBudgets);

        GetBudgetByUserIdQuery query = new GetBudgetByUserIdQuery(userId);

        // Act
        List<Budget> result = commandExecutor.execute(query);

        // Assert
        verify(budgetRepository).findByUserId(userId);
        assertEquals(expectedBudgets, result);
    }
}
