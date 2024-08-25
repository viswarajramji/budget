package com.demo.budget.event;
import com.demo.budget.enums.Category;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateExpenseEventTest {

    @Test
    public void testNoArgsConstructor() {
        CreateExpenseEvent event = new CreateExpenseEvent();
        assertThat(event).isNotNull();
    }

    @Test
    public void testAllArgsConstructor() {
        CreateExpenseEvent event = new CreateExpenseEvent(
                1L, 2L, "Expense Name", "Description", Category.EDUCATION, 100.0
        );

        assertThat(event.getExpenseId()).isEqualTo(1L);
        assertThat(event.getUserId()).isEqualTo(2L);
        assertThat(event.getExpenseName()).isEqualTo("Expense Name");
        assertThat(event.getExpenseDescription()).isEqualTo("Description");
        assertThat(event.getExpenseType()).isEqualTo(Category.EDUCATION);
        assertThat(event.getAmount()).isEqualTo(100.0);
    }

}
