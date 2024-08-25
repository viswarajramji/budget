package com.demo.budget.query;

import com.demo.budget.api.Query;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBudgetByIdQuery implements Query {
    @NotNull
    private Long id;
}

