package com.demo.budget.api;

public interface QueryExecutor<T extends Query, R> {
    R execute(T query);
}

