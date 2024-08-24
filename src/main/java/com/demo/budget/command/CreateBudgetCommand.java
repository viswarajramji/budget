package com.demo.budget.command;

import com.demo.budget.api.Command;
import com.demo.budget.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBudgetCommand implements Command {
    private Long userId;
    private ExpenseType expenseType;
    private Double amount;
}
