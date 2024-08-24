package com.demo.budget.executor;

import com.demo.budget.CommandExecutor;
import com.demo.budget.command.DeleteBudgetCommand;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteBudgetCommandExecutor implements CommandExecutor<DeleteBudgetCommand, Void> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public DeleteBudgetCommandExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Void execute(DeleteBudgetCommand command) {
        budgetRepository.deleteById(command.getId());
        return null;
    }
}
