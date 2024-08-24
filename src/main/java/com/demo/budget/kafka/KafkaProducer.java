package com.demo.budget.kafka;

import com.demo.budget.api.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final List<String> topics;
    private final ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate,
                         @Value("${kafka.producer.topics}") List<String> topics,
                         ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.topics = topics;
        this.objectMapper = objectMapper;
    }

    public void sendEvent(Event event) {
        try {
            String eventType = event.getClass().getName();
            String message = objectMapper.writeValueAsString(new KafkaEventMessage(eventType, event));
            for (String topic : topics) {
                kafkaTemplate.send(topic, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
