package com.demo.budget.executor;

import com.demo.budget.api.CommandExecutor;
import com.demo.budget.command.UpdateBudgetSpendAmountInternalCommand;
import com.demo.budget.enums.Category;
import com.demo.budget.enums.RecordType;
import com.demo.budget.event.BudgetExceedEvent;
import com.demo.budget.kafka.KafkaProducer;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import com.demo.budget.service.UserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateBudgetSpendAmountCommandExecutorTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private UpdateBudgetSpendAmountCommandExecutor commandExecutor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute_budgetNotFound() {
        // Arrange
        UpdateBudgetSpendAmountInternalCommand command = new UpdateBudgetSpendAmountInternalCommand(
                RecordType.CREATED,
                1L,
                2L,
                "ExpenseName",
                "ExpenseDescription",
                Category.EDUCATION,
                100.0,
                100.0
        );

        when(budgetRepository.findByUserIdAndBudgetType(any(Long.class), any(Category.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        try {
            commandExecutor.execute(command);
        } catch (IllegalArgumentException e) {
            // Assert
            assert e.getMessage().contains("Budget not found");
        }
    }

    @Test
    public void testExecute_budgetNotExceeding() {
        // Arrange
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setAmount(500.0);
        budget.setSpent(400.0);

        UpdateBudgetSpendAmountInternalCommand command = new UpdateBudgetSpendAmountInternalCommand(
                RecordType.CREATED,
                1L,
                2L,
                "ExpenseName",
                "ExpenseDescription",
                Category.EDUCATION,
                100.0,
                100.0
        );

        when(budgetRepository.findByUserIdAndBudgetType(any(Long.class), any(Category.class)))
                .thenReturn(Optional.of(budget));

        // Act
        commandExecutor.execute(command);

        // Assert
        verify(budgetRepository, times(1)).save(budget);
        verify(kafkaProducer, never()).sendEvent(any(BudgetExceedEvent.class));
    }

    @Test
    public void testExecute_budgetExceeding() {
        // Arrange
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setAmount(500.0);
        budget.setSpent(450.0);
        budget.setBudgetType(Category.ENTERTAINMENT);

        UpdateBudgetSpendAmountInternalCommand command = new UpdateBudgetSpendAmountInternalCommand(
                RecordType.CREATED,
                1L,
                2L,
                "ExpenseName",
                "ExpenseDescription",
                Category.EDUCATION,
                100.0,
                100.0
        );

        when(budgetRepository.findByUserIdAndBudgetType(any(Long.class), any(Category.class)))
                .thenReturn(Optional.of(budget));
        when(userValidationService.getUserEmail(any(Long.class))).thenReturn("user@example.com");

        // Act
        commandExecutor.execute(command);

        // Assert
        verify(budgetRepository, times(1)).save(budget);

    }
}

