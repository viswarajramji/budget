package com.demo.budget.event;

import com.demo.budget.api.Event;
import com.demo.budget.enums.ExpenseType;
import com.demo.budget.enums.RecordType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetExceedEvent implements Event {
    private Long expenseId;            // The ID of the expense
    private Long userId;               // The ID of the user who owns the expense
    private String expenseName;        // The name of the expense
    private String expenseDescription; // A description of the expense
    private ExpenseType expenseType;   // The type of the expense (e.g., FOOD, TRANSPORTATION, etc.)
    private Double amount;
    private Double actualAmount;          // The amount of the expense (positive or negative)
    private RecordType recordType;
    private Long budgetId;             // The ID of the budget
    private ExpenseType budgetType;         // The type of the budget
    private Double budgetAmount;       // The total budget amount
    private Double budgetSpent;
    private String emailId;// The total amount spent from the budget
}
