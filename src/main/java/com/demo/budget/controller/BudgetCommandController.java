package com.demo.budget.controller;
import com.demo.budget.command.CreateBudgetCommand;
import com.demo.budget.command.DeleteBudgetCommand;
import com.demo.budget.command.UpdateBudgetCommand;
import com.demo.budget.model.Budget;
import com.demo.budget.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets/command")
public class BudgetCommandController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetCommandController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@Valid  @RequestBody CreateBudgetCommand command) {
        Budget createdBudget = budgetService.executeCommand(command);
        return ResponseEntity.ok(createdBudget);
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<Budget> updateBudget(@Valid @PathVariable Long budgetId, @Valid @RequestBody UpdateBudgetCommand command) {
        command.setBudgetId(budgetId);
        Budget updatedBudget = budgetService.executeCommand(command);
        return ResponseEntity.ok(updatedBudget);
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteBudget(@Valid @PathVariable Long budgetId) {
        budgetService.executeCommand(new DeleteBudgetCommand(budgetId));
        return ResponseEntity.noContent().build();
    }
}
