package com.demo.budget.executor;

import com.demo.budget.api.EventExecutor;
import com.demo.budget.event.CreateExpenseEvent;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateExpenseEventExecutor implements EventExecutor<CreateExpenseEvent> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public CreateExpenseEventExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public void execute(CreateExpenseEvent event) {
        // Retrieve the user's budget based on userId
        Optional<Budget> budgetOpt = budgetRepository.findByUserIdAndExpenseType(event.getUserId(),event.getExpenseType());
        if (budgetOpt.isEmpty()) {
            throw new IllegalArgumentException("Budget not found for userId: " + event.getUserId());
        }

        Budget budget = budgetOpt.get();
        // Decrease the available budget by the expense amount
        Double newSpentAmount = budget.getSpent() + event.getAmount();
        budget.setSpent(newSpentAmount);
        // Save the updated budget
        budgetRepository.save(budget);
    }
}

