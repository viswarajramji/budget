package com.demo.budget.command;
import com.demo.budget.api.Command;
import com.demo.budget.enums.Category;
import com.demo.budget.enums.RecordType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBudgetSpendAmountInternalCommand implements Command {
    private RecordType recordType;
    private Long expenseId;           // The ID of the expense
    private Long userId;              // The ID of the user who owns the expense
    private String expenseName;       // The name of the expense
    private String expenseDescription; // A description of the expense
    private Category expenseType;  // The type of the expense (e.g., FOOD, TRANSPORTATION, etc.)
    private Double amount;
    private Double actualAmount;// The amount of the expense (positive or negative)
}
