package com.demo.budget.api;

public interface EventExecutor<T extends Event> {
    void execute(T event);
}
