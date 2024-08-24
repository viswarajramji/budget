package com.demo.budget.executor;

import com.demo.budget.api.QueryExecutor;
import com.demo.budget.query.GetBudgetByIdQuery;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetBudgetByIdCommandExecutor implements QueryExecutor<GetBudgetByIdQuery, Budget> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public GetBudgetByIdCommandExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget execute(GetBudgetByIdQuery command) {
        return budgetRepository.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("Budget not found"));
    }
}

