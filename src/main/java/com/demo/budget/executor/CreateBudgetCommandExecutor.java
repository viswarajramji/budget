package com.demo.budget.executor;


import com.demo.budget.api.CommandExecutor;
import com.demo.budget.command.CreateBudgetCommand;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import com.demo.budget.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateBudgetCommandExecutor implements CommandExecutor<CreateBudgetCommand, Budget> {

    private final BudgetRepository budgetRepository;
    private final UserValidationService userValidationService;

    @Autowired
    public CreateBudgetCommandExecutor(BudgetRepository budgetRepository, UserValidationService userValidationService) {
        this.budgetRepository = budgetRepository;
        this.userValidationService = userValidationService;
    }

    @Override
    public Budget execute(CreateBudgetCommand command) {
        if (!userValidationService.isValidUser(command.getUserId())) {
            throw new RuntimeException("Invalid userId: " + command.getUserId());
        }

        if (budgetRepository.findByUserIdAndBudgetType(command.getUserId(),command.getBudgetType()).isPresent()) {
            throw new RuntimeException("Budget Type and User Id configuration exists : " + command.getUserId());
        }

        Budget budget = new Budget();
        budget.setUserId(command.getUserId());
        budget.setBudgetType(command.getBudgetType());
        budget.setAmount(command.getAmount());
        budget.setSpent(0.0);
        return budgetRepository.save(budget);
    }
}
