package com.demo.budget.command;

import com.demo.budget.Command;
import com.demo.budget.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBudgetCommand implements Command {
    private Long userId;
    private Category category;
    private Double amount;
}
