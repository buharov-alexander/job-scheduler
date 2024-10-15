package com.bukharov.scheduler.task_runner_service.repository;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
}
