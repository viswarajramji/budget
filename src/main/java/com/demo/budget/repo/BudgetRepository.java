package com.demo.budget.repo;

import com.demo.budget.enums.Category;
import com.demo.budget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    void deleteByUserId(Long userId);

    Optional<Budget> findByUserIdAndBudgetType(Long userId, Category expenseType);

    List<Budget> findByUserId(Long userId);
}
