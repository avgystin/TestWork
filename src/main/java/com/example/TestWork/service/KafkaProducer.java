package com.example.TestWork.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "VTB_topic_2";
    private AtomicInteger counter = new AtomicInteger(0);

    public void sendProcessedMessage(String message) {
        int partition = counter.getAndIncrement() % 2;
        kafkaTemplate.send(TOPIC, partition, null, message);
    }
}