package com.demo.budget.controller;

import com.demo.budget.command.*;
import com.demo.budget.model.Budget;
import com.demo.budget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetCommandController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetCommandController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping("/command")
    public ResponseEntity<Budget> createBudget(@RequestBody CreateBudgetCommand command) {
        Budget createdBudget = budgetService.executeCommand(command);
        return ResponseEntity.ok(createdBudget);
    }

    @PutMapping("/command/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody UpdateBudgetCommand command) {
        command.setId(id);
        Budget updatedBudget = budgetService.executeCommand(command);
        return ResponseEntity.ok(updatedBudget);
    }

    @DeleteMapping("/command/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.executeCommand(new DeleteBudgetCommand(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/query/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        Budget result = budgetService.executeCommand(new GetBudgetByIdCommand(id));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/query")
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> result = budgetService.executeCommand(new GetAllBudgetsCommand());
        return ResponseEntity.ok(result);
    }
}
