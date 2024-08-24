package com.demo.budget.executor;

import com.demo.budget.CommandExecutor;
import com.demo.budget.command.DeleteUserCommand;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteBudgetsByUserIdCommandExecutor implements CommandExecutor<DeleteUserCommand, Void> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public DeleteBudgetsByUserIdCommandExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Void execute(DeleteUserCommand command) {
        budgetRepository.deleteByUserId(command.getUserId());
        return null;
    }
}
