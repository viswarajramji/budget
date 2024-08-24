package com.demo.budget.service;

import com.demo.budget.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BudgetService {

    private final ExecutionContextFactory executionContextFactory;

    @Autowired
    public BudgetService(ExecutionContextFactory executionContextFactory) {
        this.executionContextFactory = executionContextFactory;
    }

    public <T extends Command, R> R executeCommand(T command) {
        CommandExecutor<T, R> executor = executionContextFactory.getCommandExecutor((Class<T>) command.getClass());
        if (executor == null) {
            throw new IllegalArgumentException("No executor found for command: " + command.getClass().getName());
        }
        return executor.execute(command);
    }

    public <T extends Query, R> R executeQuery(T query) {
        QueryExecutor<T, R> executor = executionContextFactory.getQueryExecutor((Class<T>) query.getClass());
        if (executor == null) {
            throw new IllegalArgumentException("No executor found for query: " + query.getClass().getName());
        }
        return executor.execute(query);
    }

    public <T extends Event> void handleEvent(T event) {
        EventExecutor<T> executor = executionContextFactory.getEventExecutor((Class<T>) event.getClass());
        if (executor == null) {
            throw new IllegalArgumentException("No executor found for event: " + event.getClass().getName());
        }
        executor.execute(event);
    }
}
