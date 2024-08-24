package com.demo.budget.command;

import com.demo.budget.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBudgetByIdCommand implements Command {
    private Long id;
}

