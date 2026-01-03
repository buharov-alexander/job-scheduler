package com.bukharov.scheduler.api_service.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bukharov.scheduler.api_service.repository.TaskEntity;
import com.bukharov.scheduler.api_service.repository.TaskRepository;
import com.bukharov.scheduler.api_service.repository.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	private final Counter tasksCreatedCounter;

	@Autowired
	public TaskServiceImpl(MeterRegistry meterRegistry) {
		this.tasksCreatedCounter = meterRegistry.counter("job_scheduler_created_tasks");
	}

	@Override
	@Transactional
	public long addNewTask(String name, ZonedDateTime executionTime) {
		TaskEntity taskEntity = new TaskEntity()
				.name(name)
				.status(TaskStatus.SCHEDULED)
				.nextExecutionTime(executionTime)
				.traceId(UUID.randomUUID());
	log.debug(String.format("[TASK_TRACE] Task was created: %s", taskEntity.traceId()));
	TaskEntity saved = taskRepository.save(taskEntity);
	// increment created counter
	tasksCreatedCounter.increment();
	return saved.id();
	}

	@Override
	@Transactional
	public List<TaskDTO> getTasks(TaskStatus status) {
		return taskRepository.findByStatus(status).stream()
			.map(taskEntity -> new TaskDTO(taskEntity))
			.collect(Collectors.toList());
	}
}
