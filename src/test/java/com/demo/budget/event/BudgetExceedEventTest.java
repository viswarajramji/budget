package com.demo.budget.event;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BudgetExceedEventTest {

    @Test
    public void testNoArgsConstructor() {
        BudgetExceedEvent event = new BudgetExceedEvent();
        assertThat(event).isNotNull();
    }

    @Test
    public void testAllArgsConstructor() {
        BudgetExceedEvent event = new BudgetExceedEvent(
                1L, 2L, "Expense Name", "Description", "FOOD", 100.0,
                50.0, "Record Type", 3L, "Monthly", 500.0, 100.0, "email@example.com"
        );

        assertThat(event.getExpenseId()).isEqualTo(1L);
        assertThat(event.getUserId()).isEqualTo(2L);
        assertThat(event.getExpenseName()).isEqualTo("Expense Name");
        assertThat(event.getExpenseDescription()).isEqualTo("Description");
        assertThat(event.getExpenseType()).isEqualTo("FOOD");
        assertThat(event.getAmount()).isEqualTo(100.0);
        assertThat(event.getActualAmount()).isEqualTo(50.0);
        assertThat(event.getRecordType()).isEqualTo("Record Type");
        assertThat(event.getBudgetId()).isEqualTo(3L);
        assertThat(event.getBudgetType()).isEqualTo("Monthly");
        assertThat(event.getBudgetAmount()).isEqualTo(500.0);
        assertThat(event.getBudgetSpent()).isEqualTo(100.0);
        assertThat(event.getEmailId()).isEqualTo("email@example.com");
    }


    @Test
    public void testToString() {
        BudgetExceedEvent event = new BudgetExceedEvent(
                1L, 2L, "Expense Name", "Description", "FOOD", 100.0,
                50.0, "Record Type", 3L, "Monthly", 500.0, 100.0, "email@example.com"
        );

        String expectedToString = "BudgetExceedEvent(expenseId=1, userId=2, expenseName=Expense Name, "
                + "expenseDescription=Description, expenseType=FOOD, amount=100.0, actualAmount=50.0, "
                + "recordType=Record Type, budgetId=3, budgetType=Monthly, budgetAmount=500.0, "
                + "budgetSpent=100.0, emailId=email@example.com)";

        assertThat(event.toString()).isEqualTo(expectedToString);
    }
}
