package com.demo.budget.controller;
import com.demo.budget.command.CreateBudgetCommand;
import com.demo.budget.command.DeleteBudgetCommand;
import com.demo.budget.command.UpdateBudgetCommand;
import com.demo.budget.model.Budget;
import com.demo.budget.service.BudgetService;
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
    public ResponseEntity<Budget> createBudget(@RequestBody CreateBudgetCommand command) {
        Budget createdBudget = budgetService.executeCommand(command);
        return ResponseEntity.ok(createdBudget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody UpdateBudgetCommand command) {
        command.setId(id);
        Budget updatedBudget = budgetService.executeCommand(command);
        return ResponseEntity.ok(updatedBudget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.executeCommand(new DeleteBudgetCommand(id));
        return ResponseEntity.noContent().build();
    }
}
