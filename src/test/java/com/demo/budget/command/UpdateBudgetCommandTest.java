package com.demo.budget.command;
import com.demo.budget.enums.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UpdateBudgetCommandTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testUpdateBudgetCommand_Valid() {
        // Arrange
        Long budgetId = 1L;
        Long userId = 2L;
        Category budgetType = Category.EDUCATION;
        Double amount = 100.0;

        UpdateBudgetCommand command = new UpdateBudgetCommand(budgetId, userId, budgetType, amount);

        // Act
        Set<ConstraintViolation<UpdateBudgetCommand>> violations = validator.validate(command);

        // Assert
        assertNotNull(command);
        assertEquals(0, violations.size());  // No validation violations should occur
    }

    @Test
    void testUpdateBudgetCommand_NullFields() {
        // Arrange
        UpdateBudgetCommand command = new UpdateBudgetCommand(null, null, null, null);

        // Act
        Set<ConstraintViolation<UpdateBudgetCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(4, violations.size());  // 4 violations expected: all fields are null
    }

    @Test
    void testUpdateBudgetCommand_InvalidAmount() {
        // Arrange
        Long budgetId = 1L;
        Long userId = 2L;
        Category budgetType = Category.ENTERTAINMENT;
        Double amount = -50.0;  // Invalid negative amount

        UpdateBudgetCommand command = new UpdateBudgetCommand(budgetId, userId, budgetType, amount);

        // Act
        Set<ConstraintViolation<UpdateBudgetCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<UpdateBudgetCommand> violation = violations.iterator().next();
        assertEquals("must be greater than 0", violation.getMessage());
        assertEquals("amount", violation.getPropertyPath().toString());
    }
}
