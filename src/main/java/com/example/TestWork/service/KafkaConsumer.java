package com.example.TestWork.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final KafkaProducer kafkaProducer;
    private final MeterRegistry meterRegistry;
    private final DelayStaticService delayStaticService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Counter successCounter;
    private Timer processingTimer;

    @PostConstruct
    public void init() {
        this.successCounter = Counter.builder("kafka_consumer_success_total")
                .description("Total successfully processed messages")
                .register(meterRegistry);
        this.processingTimer = Timer.builder("kafka_consumer_processing_time")
                .description("Time to process message")
                .register(meterRegistry);
    }

    @KafkaListener(topics = "VTB_topic_1", groupId = "VTB-consumer")
    public void processMessage(String message) {
        long startTime = System.currentTimeMillis();
        Timer.Sample sample = Timer.start(meterRegistry);
        delayStaticService.applyDelay("delayToConsumer", startTime);
        try {
            JsonNode node = MAPPER.readTree(message);
            String id = node.get("id").asText();
            String processed = id + "123";
            kafkaProducer.sendProcessedMessage(processed, startTime);
            successCounter.increment();
        } catch (JsonProcessingException e) {
            log.error("Ошибка обработки: {}", message, e);
        } finally {
            sample.stop(processingTimer);
        }
    }
}