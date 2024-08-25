package com.demo.budget.controller;

import com.demo.budget.model.Budget;
import com.demo.budget.query.GetAllBudgetsQuery;
import com.demo.budget.query.GetBudgetByIdQuery;
import com.demo.budget.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets/query")
public class BudgetQueryController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetQueryController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<Budget> getBudgetById(@Valid  @PathVariable Long budgetId) {
        Budget result = budgetService.executeQuery(new GetBudgetByIdQuery(budgetId));
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> result = budgetService.executeQuery(new GetAllBudgetsQuery());
        return ResponseEntity.ok(result);
    }
}
