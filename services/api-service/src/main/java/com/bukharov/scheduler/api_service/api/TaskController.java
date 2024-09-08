package com.bukharov.scheduler.api_service.api;

import com.bukharov.scheduler.api_service.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	Logger logger = LoggerFactory.getLogger(TaskController.class);

	@PostMapping
	public long addNewTask(@RequestBody TaskDTO taskDTO) {
		return taskService.addNewTask(taskDTO.name(), taskDTO.executionTime());
	}
}
