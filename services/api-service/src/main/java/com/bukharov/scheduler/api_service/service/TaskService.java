package com.bukharov.scheduler.api_service.service;

import java.time.ZonedDateTime;

public interface TaskService {
	long addNewTask(String name, ZonedDateTime executionTime);
}
