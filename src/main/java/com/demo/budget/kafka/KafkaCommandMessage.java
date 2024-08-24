package com.demo.budget.kafka;

import com.demo.budget.Command;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaCommandMessage {
    private String commandType;
    private Command payload;
}