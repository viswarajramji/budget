package com.demo.budget.controller;

import com.demo.budget.model.Budget;
import com.demo.budget.query.GetAllBudgetsQuery;
import com.demo.budget.query.GetBudgetByUserIdQuery;
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

    @GetMapping("/{userId}")
    public ResponseEntity<List<Budget>> getBudgetById(@Valid  @PathVariable Long userId) {
        List<Budget> result = budgetService.executeQuery(new GetBudgetByUserIdQuery(userId));
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> result = budgetService.executeQuery(new GetAllBudgetsQuery());
        return ResponseEntity.ok(result);
    }
}
