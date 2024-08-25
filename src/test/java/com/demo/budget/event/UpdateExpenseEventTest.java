package com.demo.budget.event;

import com.demo.budget.enums.Category;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateExpenseEventTest {

    @Test
    public void testNoArgsConstructor() {
        UpdateExpenseEvent event = new UpdateExpenseEvent();
        assertThat(event).isNotNull();
    }

    @Test
    public void testAllArgsConstructor() {
        UpdateExpenseEvent event = new UpdateExpenseEvent(
                1L, 2L, "Expense Name", "Description", Category.EDUCATION, 150.0, 50.0
        );

        assertThat(event.getExpenseId()).isEqualTo(1L);
        assertThat(event.getUserId()).isEqualTo(2L);
        assertThat(event.getExpenseName()).isEqualTo("Expense Name");
        assertThat(event.getExpenseDescription()).isEqualTo("Description");
        assertThat(event.getExpenseType()).isEqualTo(Category.EDUCATION);
        assertThat(event.getNewAmount()).isEqualTo(150.0);
        assertThat(event.getDiffAmount()).isEqualTo(50.0);
    }


    @Test
    public void testToString() {
        UpdateExpenseEvent event = new UpdateExpenseEvent(
                1L, 2L, "Expense Name", "Description", Category.EDUCATION, 150.0, 50.0
        );

        String expectedToString = "UpdateExpenseEvent(expenseId=1, userId=2, expenseName=Expense Name, "
                + "expenseDescription=Description, expenseType=EDUCATION, newAmount=150.0, diffAmount=50.0)";

        assertThat(event.toString()).isEqualTo(expectedToString);
    }
}
