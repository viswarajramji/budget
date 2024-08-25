package com.demo.budget.command;

import com.demo.budget.api.Command;
import com.demo.budget.enums.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBudgetCommand implements Command {

    @NotNull
    private Long budgetId;

    @NotNull
    private Long userId;

    @NotNull
    private Category budgetType;

    @NotNull
    @Positive
    private Double amount;
}
