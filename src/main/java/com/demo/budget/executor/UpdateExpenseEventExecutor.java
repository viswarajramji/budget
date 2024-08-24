package com.demo.budget.executor;
import com.demo.budget.api.EventExecutor;
import com.demo.budget.event.UpdateExpenseEvent;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateExpenseEventExecutor implements EventExecutor<UpdateExpenseEvent> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public UpdateExpenseEventExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public void execute(UpdateExpenseEvent event) {
        // Retrieve the budget by userId and expenseType
        Optional<Budget> budgetOpt = budgetRepository.findByUserIdAndExpenseType(event.getUserId(), event.getExpenseType());
        if (budgetOpt.isEmpty()) {
            throw new IllegalArgumentException("Budget not found for userId: " + event.getUserId() + " and expenseType: " + event.getExpenseType());
        }

        Budget budget = budgetOpt.get();

        // Update the spent amount based on the difference in expense amount
        Double newSpentAmount = budget.getSpent() + event.getDiffAmount();
        budget.setSpent(newSpentAmount);

        // Save the updated budget
        budgetRepository.save(budget);
    }
}
