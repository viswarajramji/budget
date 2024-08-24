package com.demo.budget.kafka;

import com.demo.budget.api.Event;
import com.demo.budget.api.EventExecutor;
import com.demo.budget.api.ExecutionContextFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final ExecutionContextFactory executionContextFactory;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(ExecutionContextFactory executionContextFactory, ObjectMapper objectMapper) {
        this.executionContextFactory = executionContextFactory;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.default.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consume(String message) {
        try {
            // Parse the JSON string into a JsonNode
            JsonNode node = objectMapper.readTree(message);

            // Extract the commandType and payload
            String eventType = node.get("eventType").asText();
            JsonNode payload = node.get("payload");

            // Modify the commandType to replace the service name with "budget"
            String regex = "(com\\.demo\\.)([a-zA-Z]+)(\\.event\\.)";
            String modifiedEventType = eventType.replaceAll(regex, "$1budget$3");

            // Dynamically load the class based on the modified commandType
            Class<? extends Event> eventClass = (Class<? extends Event>) Class.forName(modifiedEventType);

            // Deserialize the payload to the appropriate Command subclass
            Event event = objectMapper.treeToValue(payload, eventClass);

            // Get the appropriate CommandExecutor and execute the command
            EventExecutor executor = executionContextFactory.getEventExecutor(event.getClass());
            executor.execute(event);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
