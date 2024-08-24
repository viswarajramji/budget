package com.demo.budget.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExecutionContextFactory {

    private final Map<Class<? extends Command>, CommandExecutor<? extends Command, ?>> commandExecutors = new HashMap<>();
    private final Map<Class<? extends Query>, QueryExecutor<? extends Query, ?>> queryExecutors = new HashMap<>();
    private final Map<Class<? extends Event>, EventExecutor<? extends Event>> eventExecutors = new HashMap<>();

    @Autowired
    public ExecutionContextFactory(Map<String, CommandExecutor<? extends Command, ?>> commandExecutors,
                                   Map<String, QueryExecutor<? extends Query, ?>> queryExecutors,
                                   Map<String, EventExecutor<? extends Event>> eventExecutors) {
        // Register Command Executors
        for (CommandExecutor<? extends Command, ?> executor : commandExecutors.values()) {
            this.commandExecutors.put(getCommandClass(executor), executor);
        }

        // Register Query Executors
        for (QueryExecutor<? extends Query, ?> executor : queryExecutors.values()) {
            this.queryExecutors.put(getQueryClass(executor), executor);
        }

        // Register Event Executors
        for (EventExecutor<? extends Event> executor : eventExecutors.values()) {
            this.eventExecutors.put(getEventClass(executor), executor);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Command, R> CommandExecutor<T, R> getCommandExecutor(Class<T> commandType) {
        return (CommandExecutor<T, R>) commandExecutors.get(commandType);
    }

    @SuppressWarnings("unchecked")
    public <T extends Query, R> QueryExecutor<T, R> getQueryExecutor(Class<T> queryType) {
        return (QueryExecutor<T, R>) queryExecutors.get(queryType);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> EventExecutor<T> getEventExecutor(Class<T> eventType) {
        return (EventExecutor<T>) eventExecutors.get(eventType);
    }

    private Class<? extends Command> getCommandClass(CommandExecutor<? extends Command, ?> executor) {
        return (Class<? extends Command>) ((java.lang.reflect.ParameterizedType)
                executor.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    private Class<? extends Query> getQueryClass(QueryExecutor<? extends Query, ?> executor) {
        return (Class<? extends Query>) ((java.lang.reflect.ParameterizedType)
                executor.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    private Class<? extends Event> getEventClass(EventExecutor<? extends Event> executor) {
        return (Class<? extends Event>) ((java.lang.reflect.ParameterizedType)
                executor.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}

