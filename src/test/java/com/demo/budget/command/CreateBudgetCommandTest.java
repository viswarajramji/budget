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

class CreateBudgetCommandTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCreateBudgetCommand_Valid() {
        // Arrange
        Long userId = 1L;
        Category budgetType = Category.EDUCATION;
        Double amount = 100.0;

        CreateBudgetCommand command = new CreateBudgetCommand(userId, budgetType, amount);

        // Act
        Set<ConstraintViolation<CreateBudgetCommand>> violations = validator.validate(command);

        // Assert
        assertNotNull(command);
        assertEquals(0, violations.size());  // No validation violations should occur
    }

    @Test
    void testCreateBudgetCommand_NullFields() {
        // Arrange
        CreateBudgetCommand command = new CreateBudgetCommand(null, null, null);

        // Act
        Set<ConstraintViolation<CreateBudgetCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(3, violations.size());  // 3 violations expected: all fields are null
    }

    @Test
    void testCreateBudgetCommand_InvalidAmount() {
        // Arrange
        Long userId = 1L;
        Category budgetType = Category.EDUCATION;
        Double amount = -50.0;  // Invalid negative amount

        CreateBudgetCommand command = new CreateBudgetCommand(userId, budgetType, amount);

        // Act
        Set<ConstraintViolation<CreateBudgetCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<CreateBudgetCommand> violation = violations.iterator().next();
        assertEquals("must be greater than 0", violation.getMessage());
        assertEquals("amount", violation.getPropertyPath().toString());
    }
}
