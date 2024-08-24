package com.demo.budget.service;

import com.demo.budget.Command;
import com.demo.budget.CommandExecutor;
import com.demo.budget.CommandExecutorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    private final CommandExecutorFactory commandExecutorFactory;

    @Autowired
    public BudgetService(CommandExecutorFactory commandExecutorFactory) {
        this.commandExecutorFactory = commandExecutorFactory;
    }

    public <T extends Command, R> R executeCommand(T command) {
        CommandExecutor<T, R> executor = commandExecutorFactory.getExecutor((Class<T>) command.getClass());
        if (executor == null) {
            throw new IllegalArgumentException("No executor found for command: " + command.getClass().getName());
        }
        return executor.execute(command);
    }
}
