package com.demo.budget.command;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeleteBudgetCommandTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testDeleteBudgetCommand_Valid() {
        // Arrange
        Long budgetId = 1L;
        DeleteBudgetCommand command = new DeleteBudgetCommand(budgetId);

        // Act
        Set<ConstraintViolation<DeleteBudgetCommand>> violations = validator.validate(command);

        // Assert
        assertNotNull(command);
        assertEquals(0, violations.size());  // No validation violations should occur
    }

    @Test
    void testDeleteBudgetCommand_NullBudgetId() {
        // Arrange
        DeleteBudgetCommand command = new DeleteBudgetCommand(null);

        // Act
        Set<ConstraintViolation<DeleteBudgetCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(1, violations.size());  // One violation expected for null budgetId
        ConstraintViolation<DeleteBudgetCommand> violation = violations.iterator().next();
        assertEquals("must not be null", violation.getMessage());
        assertEquals("budgetId", violation.getPropertyPath().toString());
    }
}
