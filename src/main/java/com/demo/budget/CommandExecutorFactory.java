package com.demo.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandExecutorFactory {

    private final Map<Class<? extends Command>, CommandExecutor<? extends Command, ?>> executors = new HashMap<>();

    @Autowired
    public CommandExecutorFactory(Map<String, CommandExecutor<? extends Command, ?>> executors) {
        for (CommandExecutor<? extends Command, ?> executor : executors.values()) {
            this.executors.put(getCommandClass(executor), executor);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Command, R> CommandExecutor<T, R> getExecutor(Class<T> commandType) {
        return (CommandExecutor<T, R>) executors.get(commandType);
    }

    private Class<? extends Command> getCommandClass(CommandExecutor<? extends Command, ?> executor) {
        return (Class<? extends Command>) ((java.lang.reflect.ParameterizedType)
                executor.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}
