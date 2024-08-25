package com.demo.budget.executor;

import com.demo.budget.api.EventExecutor;
import com.demo.budget.command.UpdateBudgetSpendAmountInternalCommand;
import com.demo.budget.enums.RecordType;
import com.demo.budget.event.DeleteExpenseEvent;
import com.demo.budget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DeleteExpenseEventExecutor implements EventExecutor<DeleteExpenseEvent> {

    private final BudgetService budgetService;

    @Autowired
    public DeleteExpenseEventExecutor(@Lazy  BudgetService budgetService) {
        this.budgetService=budgetService;
    }

    @Override
    public void execute(DeleteExpenseEvent event) {
        // Create the command with the event data
        UpdateBudgetSpendAmountInternalCommand command = new UpdateBudgetSpendAmountInternalCommand(
                RecordType.DELETED,
                event.getExpenseId(),
                event.getUserId(),
                event.getExpenseName(),
                event.getExpenseDescription(),
                event.getExpenseType(),
                event.getAmount() ,
                -event.getAmount()// Negative amount for deletion
        );

        // Execute the command
        budgetService.executeCommand(command);
    }
}
