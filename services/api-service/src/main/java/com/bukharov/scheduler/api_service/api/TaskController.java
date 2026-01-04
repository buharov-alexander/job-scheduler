package com.bukharov.scheduler.api_service.api;

import com.bukharov.scheduler.api_service.repository.TaskStatus;
import com.bukharov.scheduler.api_service.service.CreateTaskDTO;
import com.bukharov.scheduler.api_service.service.TaskDTO;
import com.bukharov.scheduler.api_service.service.TaskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping
	public long addNewTask(@RequestBody CreateTaskDTO taskDTO) {
		return taskService.addNewTask(taskDTO.name(), taskDTO.executionTime());
	}

	@GetMapping
	public List<TaskDTO> getTasks(@RequestParam(required = true) TaskStatus status) {
		return taskService.getTasks(status);
	}
}
