package com.demo.budget.command;
import com.demo.budget.enums.Category;
import com.demo.budget.enums.RecordType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UpdateBudgetSpendAmountInternalCommandTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testUpdateBudgetSpendAmountInternalCommand_Valid() {
        // Arrange
        RecordType recordType = RecordType.CREATED;
        Long expenseId = 1L;
        Long userId = 2L;
        String expenseName = "Groceries";
        String expenseDescription = "Weekly groceries";
        Category expenseType = Category.ENTERTAINMENT;
        Double amount = 100.0;
        Double actualAmount = 90.0;

        UpdateBudgetSpendAmountInternalCommand command = new UpdateBudgetSpendAmountInternalCommand(recordType, expenseId, userId, expenseName, expenseDescription, expenseType, amount, actualAmount);

        // Act
        Set<ConstraintViolation<UpdateBudgetSpendAmountInternalCommand>> violations = validator.validate(command);

        // Assert
        assertNotNull(command);
        assertEquals(0, violations.size());  // No validation violations should occur
    }

    @Test
    void testUpdateBudgetSpendAmountInternalCommand_NullFields() {
        // Arrange
        UpdateBudgetSpendAmountInternalCommand command = new UpdateBudgetSpendAmountInternalCommand(null, null, null, null, null, null, null, null);

        // Act
        Set<ConstraintViolation<UpdateBudgetSpendAmountInternalCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(0, violations.size());  // No validation violations expected as there are no annotations
    }
}
