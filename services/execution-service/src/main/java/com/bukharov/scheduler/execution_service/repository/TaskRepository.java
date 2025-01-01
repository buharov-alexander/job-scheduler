package com.bukharov.scheduler.execution_service.repository;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
}
