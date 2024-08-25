package com.demo.budget.command;

import com.demo.budget.api.Command;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBudgetCommand implements Command {
    @NotNull
    private Long budgetId;
}
