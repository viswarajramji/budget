package com.demo.budget.command;

import com.demo.budget.api.Command;
import com.demo.budget.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBudgetCommand implements Command {
    private Long id;
    private Long userId;
    private ExpenseType expenseType;
    private Double amount;
}
