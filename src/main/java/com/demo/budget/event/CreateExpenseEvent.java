package com.demo.budget.event;
import com.demo.budget.api.Event;
import com.demo.budget.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseEvent implements Event {
    private Long expenseId;
    private Long userId;
    private String expenseName;
    private String expenseDescription;
    private Category expenseType;
    private Double amount;
}
