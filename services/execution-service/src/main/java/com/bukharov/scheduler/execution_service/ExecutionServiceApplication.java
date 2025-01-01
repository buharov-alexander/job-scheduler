package com.bukharov.scheduler.execution_service;

import com.bukharov.scheduler.execution_service.kafka.TaskMessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExecutionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecutionServiceApplication.class, args);
	}

	@Bean
	public TaskMessageListener taskMessageListener() {
		return new TaskMessageListener();
	}

}
