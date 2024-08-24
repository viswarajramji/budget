package com.demo.budget.api;

public interface CommandExecutor<T extends Command, R> {
    R execute(T command);
}

