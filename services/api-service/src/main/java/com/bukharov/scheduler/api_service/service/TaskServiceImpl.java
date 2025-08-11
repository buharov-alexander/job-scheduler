package com.bukharov.scheduler.api_service.service;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.bukharov.scheduler.api_service.repository.TaskEntity;
import com.bukharov.scheduler.api_service.repository.TaskRepository;
import com.bukharov.scheduler.api_service.repository.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public long addNewTask(String name, ZonedDateTime executionTime) {
		TaskEntity taskEntity = new TaskEntity()
				.name(name)
				.status(TaskStatus.SCHEDULED)
				.nextExecutionTime(executionTime)
				.traceId(UUID.randomUUID());
		log.debug(String.format("[TASK_TRACE] Task was created: %s", taskEntity.traceId()));
		return taskRepository.save(taskEntity).id();
	}
}
