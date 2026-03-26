package com.example.TestWork.controllers;

import com.example.TestWork.configs.DelayStaticConfig;
import com.example.TestWork.service.DelayStaticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class DelayStaticController {

    private final DelayStaticConfig delayStaticConfig;
    private final DelayStaticService delayStaticService;


    @GetMapping(path = "/getDelay")
    public DelayStaticConfig getDelay() {
        return delayStaticConfig;
    }

    @PostMapping(path = "/postDelay")
    public DelayStaticConfig postDelay(@RequestBody Map<String, Object> updateDelayData)
    {
        return delayStaticService.updateDelay(delayStaticConfig, updateDelayData);
    }

    @GetMapping(path = "/resetDelay")
    private DelayStaticConfig resetDelay() {
        return delayStaticService.resetDelay();
    }
}
