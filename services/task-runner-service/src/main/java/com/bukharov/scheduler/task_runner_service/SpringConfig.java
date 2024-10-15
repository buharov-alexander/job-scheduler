package com.bukharov.scheduler.task_runner_service;

import com.bukharov.scheduler.task_runner_service.runner.TaskRunner;
import com.bukharov.scheduler.task_runner_service.runner.TaskRunnerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class SpringConfig {

	@Bean
	public TaskRunner taskRunner() {
		return new TaskRunnerImpl();
	}
}
