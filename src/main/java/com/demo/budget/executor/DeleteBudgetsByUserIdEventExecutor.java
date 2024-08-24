package com.demo.budget.executor;

import com.demo.budget.api.EventExecutor;
import com.demo.budget.event.DeleteUserEvent;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteBudgetsByUserIdEventExecutor implements EventExecutor<DeleteUserEvent> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public DeleteBudgetsByUserIdEventExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public void execute(DeleteUserEvent command) {
        budgetRepository.deleteByUserId(command.getUserId());
    }
}
