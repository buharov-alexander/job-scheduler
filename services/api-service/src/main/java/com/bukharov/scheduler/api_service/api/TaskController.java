package com.bukharov.scheduler.api_service.api;

import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/tasks")
public class TaskController {

	Logger logger = LoggerFactory.getLogger(TaskController.class);

	@PostMapping
	public void addNewTask(@RequestBody TaskDTO taskDTO) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					logger.info(String.format("Task %s was executed", taskDTO.name()));
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}, Date.from(taskDTO.start().toInstant()));
	}
}
