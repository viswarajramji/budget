package com.demo.budget.executor;
import com.demo.budget.api.QueryExecutor;
import com.demo.budget.query.GetAllBudgetsQuery;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllBudgetsCommandExecutor implements QueryExecutor<GetAllBudgetsQuery, List<Budget>> {

    private final BudgetRepository budgetRepository;

    @Autowired
    public GetAllBudgetsCommandExecutor(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public List<Budget> execute(GetAllBudgetsQuery command) {
        return budgetRepository.findAll();
    }
}
