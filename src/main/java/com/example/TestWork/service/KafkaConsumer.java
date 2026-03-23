package com.example.TestWork.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "VTB_topic_1", groupId = "VTB-consumer")
    public void processMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(message);
            String id = node.get("id").asText();
            String processed = id + "123";
            kafkaProducer.sendProcessedMessage(processed);
        } catch (JsonProcessingException e) {
            log.error("❌ Ошибка обработки: {}", message, e);
        }
    }
}