package com.bukharov.scheduler.api_service.service;

import java.time.ZonedDateTime;

import com.bukharov.scheduler.api_service.repository.TaskEntity;
import com.bukharov.scheduler.api_service.repository.TaskRepository;
import com.bukharov.scheduler.api_service.repository.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public long addNewTask(String name, ZonedDateTime executionTime) {
		TaskEntity taskEntity = new TaskEntity()
				.name(name)
				.status(TaskStatus.SCHEDULED)
				.nextExecutionTime(executionTime);
		return taskRepository.save(taskEntity).id();
	}
}
