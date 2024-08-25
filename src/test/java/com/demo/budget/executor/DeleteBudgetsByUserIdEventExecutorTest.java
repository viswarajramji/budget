package com.demo.budget.executor;
import com.demo.budget.event.DeleteUserEvent;
import com.demo.budget.repo.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class DeleteBudgetsByUserIdEventExecutorTest {

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private DeleteBudgetsByUserIdEventExecutor eventExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute() {
        // Arrange
        Long userId = 1L;
        DeleteUserEvent event = new DeleteUserEvent(userId);

        // Act
        eventExecutor.execute(event);

        // Assert
        verify(budgetRepository).deleteByUserId(userId);
    }
}
