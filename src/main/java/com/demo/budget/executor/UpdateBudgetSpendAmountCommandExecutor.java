package com.demo.budget.executor;

import com.demo.budget.api.CommandExecutor;
import com.demo.budget.command.UpdateBudgetSpendAmountInternalCommand;
import com.demo.budget.event.BudgetExceedEvent;
import com.demo.budget.kafka.KafkaProducer;
import com.demo.budget.model.Budget;
import com.demo.budget.repo.BudgetRepository;
import com.demo.budget.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateBudgetSpendAmountCommandExecutor implements CommandExecutor<UpdateBudgetSpendAmountInternalCommand, Void> {

    private final BudgetRepository budgetRepository;
    private final UserValidationService userValidationService;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public UpdateBudgetSpendAmountCommandExecutor(BudgetRepository budgetRepository, UserValidationService userValidationService,KafkaProducer kafkaProducer) {
        this.budgetRepository = budgetRepository;
        this.userValidationService=userValidationService;
        this.kafkaProducer=kafkaProducer;
    }

    @Override
    public Void execute(UpdateBudgetSpendAmountInternalCommand command) {
        // Fetch the budget for the user and specific expense type
        Budget budget = budgetRepository.findByUserIdAndBudgetType(command.getUserId(), command.getExpenseType())
                .orElseThrow(() -> new IllegalArgumentException("Budget not found for userId: " + command.getUserId() + " and expenseType: " + command.getExpenseType()));

        // Update the spent amount by adding the command's amount
        budget.setSpent(budget.getSpent() + command.getActualAmount());

        // Save the updated budget
        budgetRepository.save(budget);

        if (budget.getSpent() > budget.getAmount()) {
            // Fetch the user's email
            String userEmail = userValidationService.getUserEmail(command.getUserId());

            // Create and trigger the BudgetExceedEvent
            BudgetExceedEvent event = new BudgetExceedEvent(
                    command.getExpenseId(),
                    command.getUserId(),
                    command.getExpenseName(),
                    command.getExpenseDescription(),
                    command.getExpenseType().name(),
                    command.getAmount(),
                    command.getActualAmount(),
                    command.getRecordType().name(),
                    budget.getId(),
                    budget.getBudgetType().name(),
                    budget.getAmount(),
                    budget.getSpent(),
                    userEmail
            );

            kafkaProducer.sendEvent(event);
        }
        return null;
    }
}
