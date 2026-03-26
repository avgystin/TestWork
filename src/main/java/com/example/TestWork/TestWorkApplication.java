package com.example.TestWork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TestWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestWorkApplication.class, args);
	}

}
