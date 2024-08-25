package com.demo.budget.executor;

import com.demo.budget.api.EventExecutor;
import com.demo.budget.command.UpdateBudgetSpendAmountInternalCommand;
import com.demo.budget.enums.RecordType;
import com.demo.budget.event.CreateExpenseEvent;
import com.demo.budget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CreateExpenseEventExecutor implements EventExecutor<CreateExpenseEvent> {

    private final BudgetService budgetService;


    @Autowired
    public CreateExpenseEventExecutor(@Lazy  BudgetService budgetService) {
        this.budgetService=budgetService;
    }

    @Override
    public void execute(CreateExpenseEvent event) {
        // Create the command with the event data
        UpdateBudgetSpendAmountInternalCommand command = new UpdateBudgetSpendAmountInternalCommand(
                RecordType.CREATED,
                event.getExpenseId(),
                event.getUserId(),
                event.getExpenseName(),
                event.getExpenseDescription(),
                event.getExpenseType(),
                event.getAmount(),
                event.getAmount()// Positive amount for creation
        );

        // Execute the command
        budgetService.executeCommand(command);
    }
}

