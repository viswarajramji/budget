package com.demo.budget.executor;

import com.demo.budget.api.CommandExecutor;
import com.demo.budget.command.UpdateBudgetCommand;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import com.demo.budget.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateBudgetCommandExecutor implements CommandExecutor<UpdateBudgetCommand, Budget> {

    private final BudgetRepository budgetRepository;
    private final UserValidationService userValidationService;

    @Autowired
    public UpdateBudgetCommandExecutor(BudgetRepository budgetRepository, UserValidationService userValidationService) {
        this.budgetRepository = budgetRepository;
        this.userValidationService = userValidationService;
    }

    @Override
    public Budget execute(UpdateBudgetCommand command) {
        if (!userValidationService.isValidUser(command.getUserId())) {
            throw new IllegalArgumentException("Invalid userId: " + command.getUserId());
        }

        Budget budget = budgetRepository.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        budget.setCategory(command.getCategory());
        budget.setAmount(command.getAmount());
        return budgetRepository.save(budget);
    }
}
