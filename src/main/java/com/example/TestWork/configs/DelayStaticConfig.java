package com.example.TestWork.configs;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "response.delay.static")
public class DelayStaticConfig {
    private long defaultDelay;
    private long delayToConsumer;
    private long delayToProducer;
}
