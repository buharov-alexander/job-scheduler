package com.bukharov.scheduler.api_service.service;

import java.time.ZonedDateTime;
import java.util.List;

import com.bukharov.scheduler.api_service.repository.TaskStatus;

public interface TaskService {
	long addNewTask(String name, ZonedDateTime executionTime);

    List<TaskDTO> getTasks(TaskStatus status);
}
