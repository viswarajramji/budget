package com.demo.budget.executor;

import com.demo.budget.api.EventExecutor;
import com.demo.budget.event.DeleteExpenseEvent;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteExpenseEventExecutor implements EventExecutor<DeleteExpenseEvent> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public DeleteExpenseEventExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public void execute(DeleteExpenseEvent event) {
        // Retrieve the budget by userId and expenseType
        Optional<Budget> budgetOpt = budgetRepository.findByUserIdAndExpenseType(event.getUserId(), event.getExpenseType());
        if (budgetOpt.isEmpty()) {
            throw new IllegalArgumentException("Budget not found for userId: " + event.getUserId() + " and expenseType: " + event.getExpenseType());
        }

        Budget budget = budgetOpt.get();
        // Increase the available budget by the deleted expense amount
        Double newSpentAmount = budget.getSpent() - event.getAmount();
        budget.setSpent(newSpentAmount);

        // Save the updated budget
        budgetRepository.save(budget);
    }
}
