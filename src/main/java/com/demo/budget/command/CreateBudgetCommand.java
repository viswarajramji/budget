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
public class CreateBudgetCommand implements Command {

    @NotNull
    private Long userId;

    @NotNull
    private Category budgetType;

    @NotNull
    @Positive
    private Double amount;
}
