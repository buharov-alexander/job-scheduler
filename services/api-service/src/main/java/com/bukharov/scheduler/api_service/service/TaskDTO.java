package com.bukharov.scheduler.api_service.service;

import java.time.ZonedDateTime;

import com.bukharov.scheduler.api_service.repository.TaskEntity;
import com.bukharov.scheduler.api_service.repository.TaskStatus;

public record TaskDTO(
	Long id, String name, ZonedDateTime executionTime, TaskStatus status) {

		TaskDTO(String name, ZonedDateTime executionTime) {
			this(null, name, executionTime, null);
		}

		TaskDTO(TaskEntity entity) {
			this(
				entity.id(),
				entity.name(),
				entity.nextExecutionTime(),
				entity.status()
			);
		}
}
