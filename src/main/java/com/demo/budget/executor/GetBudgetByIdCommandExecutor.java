package com.demo.budget.executor;

import com.demo.budget.api.QueryExecutor;
import com.demo.budget.query.GetBudgetByUserIdQuery;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBudgetByIdCommandExecutor implements QueryExecutor<GetBudgetByUserIdQuery, List<Budget>> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public GetBudgetByIdCommandExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public List<Budget> execute(GetBudgetByUserIdQuery command) {
        return budgetRepository.findByUserId(command.getUserId());
    }
}

