package com.demo.budget.kafka;

import com.demo.budget.api.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEventMessage {
    private String eventType;
    private Event payload;
}