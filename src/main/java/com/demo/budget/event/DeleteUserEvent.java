package com.demo.budget.event;
import com.demo.budget.api.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserEvent implements Event {
    private Long userId;
}