package com.demo.budget;

public interface CommandExecutor<T extends Command, R> {
    R execute(T command);
}
