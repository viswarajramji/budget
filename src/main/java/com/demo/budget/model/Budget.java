package com.demo.budget.model;

import com.demo.budget.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "budget", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "budgetType"})
})
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;  // Link to the user

    @Enumerated(EnumType.STRING)
    private Category budgetType;  // Use enum for category

    private Double amount;

    private Double spent;
}
