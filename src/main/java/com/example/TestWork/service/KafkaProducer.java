package com.example.TestWork.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MeterRegistry meterRegistry;
    private final DelayStaticService delayStaticService;
    private static final String TOPIC = "VTB_topic_2";
    private AtomicInteger counter = new AtomicInteger(1);
    private Timer sendTimer;
    private Counter sendCounter;

    @PostConstruct
    public void init() {
        this.sendCounter = Counter.builder("kafka_producer_sent_total")
                .description("Total messages sent")
                .register(meterRegistry);
        this.sendTimer = Timer.builder("kafka_producer_send_time")
                .description("Time to send message")
                .register(meterRegistry);
    }
    public void sendProcessedMessage(String message, long startConsumerTime) {
        Timer.Sample sample = Timer.start(meterRegistry);
        long startTime = System.currentTimeMillis();
        delayStaticService.applyDelay("delayToProducer", startTime);
        try {
            int partition = counter.getAndIncrement() % 2;
            delayStaticService.applyDelay("delayToKafka", startConsumerTime);
            kafkaTemplate.send(TOPIC, partition, null, message);
            sendCounter.increment();
        } finally {
            sample.stop(sendTimer);
        }
    }
}