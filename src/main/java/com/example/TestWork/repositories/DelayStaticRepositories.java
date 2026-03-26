package com.example.TestWork.repositories;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@Data
@Component
@ConfigurationProperties(prefix = "response.delay.static")
public class DelayStaticRepositories {
    private long defaultDelay;
    private long delayToConsumer;
    private long delayToProducer;


    public Map<String, Object> getOriginalFromYaml() {
        return Map.of(
                "defaultDelay", defaultDelay,
                "delayToConsumer", delayToConsumer,
                "delayToProducer", delayToProducer
        );
    }
}
