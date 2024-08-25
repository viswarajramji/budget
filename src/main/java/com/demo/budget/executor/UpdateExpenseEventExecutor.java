package com.demo.budget.executor;
import com.demo.budget.api.EventExecutor;
import com.demo.budget.command.UpdateBudgetSpendAmountInternalCommand;
import com.demo.budget.enums.RecordType;
import com.demo.budget.event.UpdateExpenseEvent;
import com.demo.budget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UpdateExpenseEventExecutor implements EventExecutor<UpdateExpenseEvent> {

    private final BudgetService budgetService;

    @Autowired
    public UpdateExpenseEventExecutor(@Lazy  BudgetService budgetService) {
        this.budgetService=budgetService;
    }

    @Override
    public void execute(UpdateExpenseEvent event) {
        // Create the command with the event data
        UpdateBudgetSpendAmountInternalCommand command = new UpdateBudgetSpendAmountInternalCommand(
                RecordType.MODIFIED,
                event.getExpenseId(),
                event.getUserId(),
                event.getExpenseName(),
                event.getExpenseDescription(),
                event.getExpenseType(),
                event.getNewAmount(),
                event.getDiffAmount()// Diff amount for update (can be positive or negative)
        );

        // Execute the command
        budgetService.executeCommand(command);
    }
}
