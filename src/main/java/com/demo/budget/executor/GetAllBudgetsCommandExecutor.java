package com.demo.budget.executor;
import com.demo.budget.CommandExecutor;
import com.demo.budget.command.GetAllBudgetsCommand;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllBudgetsCommandExecutor implements CommandExecutor<GetAllBudgetsCommand, List<Budget>> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public GetAllBudgetsCommandExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public List<Budget> execute(GetAllBudgetsCommand command) {
        return budgetRepository.findAll();
    }
}
