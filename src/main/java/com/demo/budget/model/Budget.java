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

    @Column(nullable = false)
    private Long userId;  // Link to the user

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category budgetType;  // Use enum for category

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double spent;
}
