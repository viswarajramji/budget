package com.demo.budget.command;

import com.demo.budget.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserCommand implements Command {
    private Long userId;
}