package com.example.TestWork.service;

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
        String processed = message + "123";
        kafkaProducer.sendProcessedMessage(processed);
    }
}